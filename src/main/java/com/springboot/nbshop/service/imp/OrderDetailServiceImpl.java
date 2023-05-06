package com.springboot.nbshop.service.imp;

import com.springboot.nbshop.entity.OrderDetail;
import com.springboot.nbshop.entity.OrderDetailPK;
import com.springboot.nbshop.reponsitory.OrderDetailRepository;
import com.springboot.nbshop.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getAllOrderDetail() {
        return orderDetailRepository.findAll();
    }

    @Override
    public void updateOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public void removeOrderById(Integer order_id,Long product_id) {
        orderDetailRepository.deleteById(new OrderDetailPK(product_id,order_id));
    }

    @Override
    public List<OrderDetail> getOrder(Integer id) {
        return orderDetailRepository.getOrder(id);
    }

    public void deleteDetailOrder(Integer order_id){
        orderDetailRepository.deleteDetailOrder(order_id);
    }


}
