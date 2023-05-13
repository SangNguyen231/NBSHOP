package com.springboot.nbshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.nbshop.entity.Category;

@Service
public interface CategoryService {

	public List<Category> getAllCategory();

	public void updateCategory(Category category);

	public void removeCategoryById(int id);

	public Optional<Category> getCategoryById(int id);

}
