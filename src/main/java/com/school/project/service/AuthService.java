package com.school.project.service;

import com.school.project.dto.LoginRequestDTO;
import com.school.project.dto.UserResponseDTO;
import com.school.project.mapper.ProductMapper;
import com.school.project.mapper.UserMapper;
import com.school.project.models.User;
import com.school.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public UserResponseDTO register(LoginRequestDTO dto) {
        User user = new User();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("USER");

        User saved = userRepository.save(user);

        return mapper.toUserResponse(saved);
    }

    public String login(LoginRequestDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return jwtService.generateToken(user.getUsername(), user.getRole());
    }
}
