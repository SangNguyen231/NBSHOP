package com.springboot.nbshop.reponsitory;


import com.springboot.nbshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_Id(int id);

    @Query(
            value = "SELECT *,[dbo].[ufn_removeMark](name)  FROM Product where [dbo].[ufn_removeMark](name) like ?1",
            nativeQuery = true)
    public List<Product> finProductsByProductName(String name);
}
