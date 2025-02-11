package com.example.idus_exam.user;

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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
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
    public UserDto.UserPageResponse getUserList(int page, int size) {
        Page<User> result = userRepository.findAll(PageRequest.of(page, size));
        return UserDto.UserPageResponse.from(result);
    }
}
