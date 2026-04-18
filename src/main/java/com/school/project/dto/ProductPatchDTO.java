package com.school.project.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Partial update for product")
public class ProductPatchDTO {
    @Schema(description = "Product name", example = "Mouse Gamer")
    private String name;

    @Schema(description = "Product price", example = "150.0")
    private Double price;

    @Schema(description = "Product quantity", example = "10")
    private Integer quantity;

    @Schema(description = "Product description", example = "RGB gaming mouse")
    private String description;
}
