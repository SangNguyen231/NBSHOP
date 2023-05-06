package com.springboot.nbshop.service;


import com.springboot.nbshop.entity.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {

    public List<OrderDetail> getAllOrderDetail();

    public void updateOrderDetail(OrderDetail orderDetail);

    public void removeOrderById(Integer order_id,Long product_id);

    public List<OrderDetail> getOrder(Integer id);

    public void deleteDetailOrder(Integer order_id);
}
