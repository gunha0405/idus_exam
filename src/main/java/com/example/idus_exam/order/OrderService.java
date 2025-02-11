package com.example.idus_exam.order;

import com.example.idus_exam.order.model.Order;
import com.example.idus_exam.order.model.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<OrderDto.OrderResponse> getOrderList(Long userIdx) {
        List<Order> entityList = orderRepository.findAllByUserIdx(userIdx);
        return entityList.stream()
                .map(order -> new OrderDto.OrderResponse(
                        order.getOrderNo(),
                        order.getProductName(),
                        order.getOrderDate()
                ))
                .collect(Collectors.toList());
    }
}
