package com.springboot.nbshop.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.nbshop.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
