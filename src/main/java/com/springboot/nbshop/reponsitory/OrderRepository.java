package com.springboot.nbshop.reponsitory;

import com.springboot.nbshop.entity.Order;
import com.springboot.nbshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(
            value = "SELECT * FROM orders WHERE user_id = ?1",
            nativeQuery = true)
    public List<Order> getOrderByUser(Integer user_id);
}
