package com.school.project.controller;


import com.school.project.dto.ApiResponse;
import com.school.project.dto.LoginRequestDTO;
import com.school.project.dto.UserResponseDTO;
import com.school.project.models.User;
import com.school.project.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication operations")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account"
    )
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@RequestBody @Valid LoginRequestDTO dto) {
        UserResponseDTO user = authService.register(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        true,
                        user,
                        "Registration successful"
                )
        );
    }

    @Operation(
            summary = "User login",
            description = "Authenticates user and returns JWT token"
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Valid LoginRequestDTO dto) {

        String token = authService.login(dto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        true,
                        token,
                        "Logged in successfully"

                )
        );
    }

}
