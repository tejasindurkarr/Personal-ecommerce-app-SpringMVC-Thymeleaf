package com.tejasandjava.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejasandjava.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
