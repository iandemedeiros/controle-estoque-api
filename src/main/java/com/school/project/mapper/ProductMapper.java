package com.school.project.mapper;

import com.school.project.dto.ProductPatchDTO;
import com.school.project.dto.ProductRequestDTO;
import com.school.project.dto.ProductResponseDTO;
import com.school.project.dto.UserResponseDTO;
import com.school.project.models.Product;
import com.school.project.models.User;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDTO toResponse(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getDescription()
        );
    }

    public Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setDescription(dto.getDescription());
        return product;
    }

    public void updateEntity(ProductRequestDTO dto, Product product) {
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setDescription(dto.getDescription());
    }

    public void PatchEntity(ProductPatchDTO dto, Product product) {
        if (dto.getName() != null) {
            product.setName(dto.getName());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getQuantity() != null) {
            product.setQuantity(dto.getQuantity());
        }
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }

    }

}
