package com.example.idus_exam.user.model;

import com.example.idus_exam.order.model.Order;
import com.example.idus_exam.order.model.OrderDto;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    @Getter
    public static class SignupRequest {
        private String name;
        private String nickname;
        private String password;
        private String phoneNumber;
        private String email;
        private String gender;

        public User toEntity(String encodedPassword) {
            return User.builder().name(name).nickName(nickname).password(encodedPassword).phoneNumber(phoneNumber).email(email).gender(gender).build();
        }
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignupResponse {
        private Long idx;
        private String nickname;
        public static SignupResponse from(User user) {
            return new SignupResponse(user.getIdx(), user.getNickName());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserInfoResponse {
        private String name;
        private String nickName;
        private String phoneNumber;
        private String email;
        private String gender;
        private OrderDto.LastOrderResponse lastOrder;
        public static UserInfoResponse from(User user, Order lastOrder) {
            return new UserInfoResponse(
                    user.getName(),
                    user.getNickName(),
                    user.getPhoneNumber(),
                    user.getEmail(),
                    user.getGender(),
                    lastOrder != null ? OrderDto.LastOrderResponse.from(lastOrder) : null
            );
        }
        public static UserInfoResponse from(User user) {
            return UserInfoResponse.builder().name(user.getName()).nickName(user.getNickName()).phoneNumber(user.getPhoneNumber()).email(user.getEmail()).gender(user.getGender()).build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserPageResponse {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
        private boolean hasNext;
        private boolean hasPrevious;

        private List<UserInfoResponse> userList;

        public static UserPageResponse from(Page<User> userPage) {
            return UserPageResponse.builder()
                    .page(userPage.getNumber())
                    .size(userPage.getSize())
                    .totalElements(userPage.getTotalElements())
                    .totalPages(userPage.getTotalPages())
                    .hasNext(userPage.hasNext())
                    .hasPrevious(userPage.hasPrevious())
                    .userList(userPage.stream().map(UserDto.UserInfoResponse::from).collect(Collectors.toList()))
                    .build();
        }

    }

}
