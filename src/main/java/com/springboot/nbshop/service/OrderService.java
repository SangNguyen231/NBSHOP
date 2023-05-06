package com.springboot.nbshop.service;


import com.springboot.nbshop.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    public List<Order> getAllOrder();

    public void updateOrder(Order order);

    public void removeOrderById(Integer id);

    public Optional<Order> getOrderById(Integer id);

    public List<Order> getOrderByUser(Integer user_id);
}
