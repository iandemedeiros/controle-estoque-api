package com.school.project.mapper;

import com.school.project.dto.UserResponseDTO;
import com.school.project.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toUserResponse(User user) {
        return new UserResponseDTO(
                user.getUsername()
        );
    }
}
