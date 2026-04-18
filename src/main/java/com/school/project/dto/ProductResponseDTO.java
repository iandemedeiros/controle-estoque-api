package com.school.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Product response data")
public class ProductResponseDTO {

    @Schema(description = "Product ID", example = "1")
    private Long id;

    @Schema(description = "Product name", example = "Mouse Gamer")
    private String name;

    @Schema(description = "Product price", example = "150.0")
    private Double price;

    @Schema(description = "Product quantity", example = "10")
    private Integer quantity;

    @Schema(description = "Product description", example = "RGB gaming mouse")
    private String description;

}
