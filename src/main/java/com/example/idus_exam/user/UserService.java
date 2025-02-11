package com.example.idus_exam.user;

import com.example.idus_exam.order.OrderRepository;
import com.example.idus_exam.order.model.Order;
import com.example.idus_exam.user.model.User;
import com.example.idus_exam.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByEmail(username);

        if (result.isPresent()) {
            User user = result.get();
            return user;
        }

        return null;
    }

    @Transactional
    public UserDto.SignupResponse signup(UserDto.SignupRequest request) {
        User user = userRepository.save(request.toEntity(passwordEncoder.encode(request.getPassword())));

        return UserDto.SignupResponse.from(user);
    }

    @Transactional(readOnly = true)
    public UserDto.UserInfoResponse getUserInfo(Long userIdx) {
        Optional<User> result = userRepository.findById(userIdx);
        if (result.isPresent()) {
            return UserDto.UserInfoResponse.from(result.get());
        }
        return null;
    }

    @Transactional(readOnly = true)
    public UserDto.UserPageResponse getUserList(String name, String email, int page, int size) {
        Page<User> result = userRepository.findByNameContainingOrEmailContaining(name, email, PageRequest.of(page, size));

        List<UserDto.UserInfoResponse> userInfoList = result.stream()
                .map(user -> {
                    // 회원의 마지막 주문 가져오기
                    Order lastOrder = orderRepository.findTopByUserIdxOrderByOrderDateDesc(user.getIdx()).orElse(null);
                    return UserDto.UserInfoResponse.from(user, lastOrder);
                })
                .collect(Collectors.toList());

        return UserDto.UserPageResponse.builder()
                .page(result.getNumber())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .hasNext(result.hasNext())
                .hasPrevious(result.hasPrevious())
                .userList(userInfoList)
                .build();
    }


}
