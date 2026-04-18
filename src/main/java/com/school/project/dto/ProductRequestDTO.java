package com.school.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Data to create or update a product")
public class ProductRequestDTO {

    @Schema(description = "Product name", example = "Mouse Gamer")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "Product price", example = "150.0")
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @Schema(description = "Product quantity", example = "10")
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @Schema(description = "Product description", example = "RGB gaming mouse")
    private String description;

}
