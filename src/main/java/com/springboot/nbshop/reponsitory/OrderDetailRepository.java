package com.springboot.nbshop.reponsitory;

import com.springboot.nbshop.entity.OrderDetail;
import com.springboot.nbshop.entity.OrderDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailPK> {
    @Query(
            value = "SELECT * FROM OrderDetail u WHERE u.order_id = ?1",
            nativeQuery = true)
    public List<OrderDetail> getOrder(Integer order_id);


    @Query(
            value = "DELETE FROM OrderDetail  WHERE order_id = ?1",
            nativeQuery = true)
    public void deleteDetailOrder(Integer order_id);

}
