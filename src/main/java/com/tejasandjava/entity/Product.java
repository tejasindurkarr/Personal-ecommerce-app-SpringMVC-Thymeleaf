package com.tejasandjava.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Product name can not be blank")
	@Size(min=5, max=10,message = "Product Name Should be min 5 and Max 10 Char")
	private String name;
	
	@NotNull(message = "product price can not be Null")
	@Positive(message= "Price should be positive")
	private Double price;
	
	@NotNull(message ="product qty can not be Null")
	@Positive(message= "QTY should be positive")
	private Integer qty;
	
	@Lob
    private byte[] image;

}
