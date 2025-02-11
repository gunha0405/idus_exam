package com.example.idus_exam.order;

import com.example.idus_exam.order.model.OrderDto;
import com.example.idus_exam.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/myOrderList")
    public ResponseEntity<List<OrderDto.OrderResponse>> getOrderList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Long userIdx = user.getIdx();
        List<OrderDto.OrderResponse> orderList = orderService.getOrderList(userIdx);
        return ResponseEntity.ok(orderList);
    }

}
