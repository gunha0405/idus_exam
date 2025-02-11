package com.example.idus_exam.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

public class OrderDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderResponse {
        private Long orderNo;
        private String productName;
        private String orderDate;

        public static OrderResponse from(Order order) {
            return new OrderResponse(order.getOrderNo(), order.getProductName(), order.getOrderDate());
        }

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LastOrderResponse {
        private Long orderNo;
        private String productName;
        private String orderDate;

        public static LastOrderResponse from(Order order) {
            return new LastOrderResponse(
                    order.getOrderNo(),
                    order.getProductName(),
                    order.getOrderDate()
            );
        }
    }

}
