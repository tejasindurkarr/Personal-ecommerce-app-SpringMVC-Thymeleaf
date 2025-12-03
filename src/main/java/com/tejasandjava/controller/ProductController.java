package com.tejasandjava.controller;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tejasandjava.entity.Product;
import com.tejasandjava.repo.ProductRepository;

import jakarta.validation.Valid;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping({"/", "/home", "/ecommars"})
    public String home() {
        return "home";
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/form")
    public String getForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid Product product, BindingResult result,
                              @RequestParam("imageFile") MultipartFile file,
                              Model model) throws IOException {

        if (result.hasErrors()) {
            return "product-form";
        }

        if (!file.isEmpty()) {
            product.setImage(file.getBytes());
        }

        productRepository.save(product);
        model.addAttribute("product", new Product()); // reset form after save
        model.addAttribute("msg", "Product Saved Successfully!");
        return "product-form";
    }

    @GetMapping("/edit")
    public String editProduct(@RequestParam("id") int id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
        model.addAttribute("product", product);
        return "product-form";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") int id, RedirectAttributes ra) {
        productRepository.deleteById(id);
        ra.addFlashAttribute("msg", "Product Deleted Successfully!");
        return "redirect:/products";
    }
    
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Product p = productRepository.findById(id).orElse(null);
        if (p == null || p.getImage() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(p.getImage());
    }

}
