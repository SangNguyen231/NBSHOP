package com.springboot.nbshop.service.imp;

import com.springboot.nbshop.entity.Order;
import com.springboot.nbshop.reponsitory.OrderRepository;
import com.springboot.nbshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void removeOrderById(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrderByUser(Integer user_id) {
        return orderRepository.getOrderByUser(user_id);
    }
}
