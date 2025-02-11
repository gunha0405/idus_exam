package com.example.idus_exam.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    public static class UserInfoResponse {
        private String name;
        private String nickName;
        private String phoneNumber;
        private String email;
        private String gender;
        public static UserInfoResponse from(User user) {
            return new UserInfoResponse(user.getName(), user.getNickName(), user.getPhoneNumber(), user.getEmail(), user.getGender());
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
