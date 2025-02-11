package com.example.idus_exam.user;

import com.example.idus_exam.user.model.User;
import com.example.idus_exam.user.model.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto.SignupResponse> signup(@RequestBody UserDto.SignupRequest request) {
        UserDto.SignupResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("ATOKEN", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(0) // 즉시 만료
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("로그아웃 성공");
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto.UserInfoResponse> userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Long userIdx = user.getIdx();
        UserDto.UserInfoResponse response = userService.getUserInfo(userIdx);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<UserDto.UserPageResponse> userList(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name,
            @RequestParam int page,
            @RequestParam int size
    ) {
        UserDto.UserPageResponse list = userService.getUserList(name, email, page, size);
        return ResponseEntity.ok(list);
    }

}
