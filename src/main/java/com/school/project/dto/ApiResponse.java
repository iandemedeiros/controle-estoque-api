package com.school.project.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Standard API response wrapper")
public class ApiResponse<T> {

    @Schema(description = "Indicates if the request was successful", example = "true")
    private Boolean success;

    @Schema(description = "Response data payload (can be object, list or null)")
    private T data;

    @Schema(description = "Human-readable message about the result", example = "Product created successfully")
    private String message;
}
