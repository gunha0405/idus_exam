package com.example.idus_exam.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
