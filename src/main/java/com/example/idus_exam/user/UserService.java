package com.example.idus_exam.user;

import com.example.idus_exam.user.model.User;
import com.example.idus_exam.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByEmail(username);

        if (result.isPresent()) {
            User user = result.get();
            return user;
        }

        return null;
    }

    public UserDto.SignupResponse signup(UserDto.SignupRequest request) {
        User user = userRepository.save(request.toEntity(passwordEncoder.encode(request.getPassword())));

        return UserDto.SignupResponse.from(user);
    }

    public UserDto.UserInfoResponse getUserInfo(Long userIdx) {
        Optional<User> result = userRepository.findById(userIdx);
        if (result.isPresent()) {
            return UserDto.UserInfoResponse.from(result.get());
        }
        return null;
    }
}
