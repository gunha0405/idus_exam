package com.example.idus_exam.order.model;


import com.example.idus_exam.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long orderNo;
    private String productName;
    private String orderDate;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

}
