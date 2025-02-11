package com.example.idus_exam.order;

import com.example.idus_exam.order.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByUserIdx(Long userIdx);
}
