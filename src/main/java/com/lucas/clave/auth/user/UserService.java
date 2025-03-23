package com.lucas.clave.auth.user;

import com.lucas.clave.auth.user.entity.User;
import com.lucas.clave.auth.user.model.UserCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Password must be already encoded before calling this method
    public User create(UserCreate userCreate) {
        User user = User.builder()
                .name(userCreate.getName())
                .email(userCreate.getEmail())
                .password(userCreate.getPassword())
                .role(userCreate.getRole())
                .build();

        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
