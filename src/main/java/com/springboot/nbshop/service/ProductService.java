package com.springboot.nbshop.service;


import com.springboot.nbshop.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

	List<Product> getAllProductByCategoryId(int id);

	Optional<Product> getProductById(long id);

	void removeProductById(long id);

	void updateProduct(Product product);

	List<Product> getAllProduct();

	public List<Product> finProductsByProductName(String name);
    
}
