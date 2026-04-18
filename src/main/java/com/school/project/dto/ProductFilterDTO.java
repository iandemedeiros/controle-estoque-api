package com.school.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Filter parameters for products")
public class ProductFilterDTO {

    @Schema(description = "Filter by product name", example = "mouse")
    private String name;
    @Schema(description = "Minimum price", example = "10.0")
    private Double minPrice;
    @Schema(description = "Maximum price", example = "1000.0")
    private Double maxPrice;

    @AssertTrue(message = " minPrice cannot be greater than maxPrice")
    public boolean isPriceValid() {
        if (minPrice == null || maxPrice == null) return true;
        return minPrice <= maxPrice;
    }
}
