package com.school.project.controller;

import com.school.project.dto.*;
import com.school.project.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Operations related to products")
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "List all products",
            description = "Returns a paginated list of products with optional filters (name, minPrice, maxPrice)"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponseDTO>>> getProducts(@ModelAttribute @Valid ProductFilterDTO filter, Pageable pageable) {
        Page<ProductResponseDTO> data = productService.getAllProducts(filter, pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        data,
                        "Products retrieved successfully"
                )
        );
    }

    @Operation(summary = "Get product by ID")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.getProductById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        product,
                        "Product found successfully"
                )
        );
    }

    @Operation(summary = "Create a new product", description = "Creates a product with name, price, quantity and description")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(@RequestBody @Valid ProductRequestDTO dto) {
        ProductResponseDTO product = productService.createProduct(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        true,
                        product,
                        "Product created successfully"
                )
        );
    }

    @Operation(
            summary = "Create multiple products",
            description = "Creates a list of products in a single request (all-or-nothing)"
    )
    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> createAll(@RequestBody List<@Valid ProductRequestDTO> dtos) {

        List<ProductResponseDTO> products = productService.createBatch(dtos);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        true,
                        products,
                        "All products created successfully"
                )
        );
    }

    @Operation(
            summary = "Update a product by ID",
            description = "Update all product fields. Requires full object"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> update(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO dto) {

        ProductResponseDTO product = productService.updateProduct(id, dto);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        product,
                        "Product updated successfully"
                )
        );
    }

    @Operation(
            summary = "Partially update a product",
            description = "Updates only provided fields (partial update)"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> patch(@PathVariable Long id, @RequestBody @Valid ProductPatchDTO dto) {
        ProductResponseDTO product = productService.patchProduct(id, dto);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        product,
                        "Product patched successfully"
                )
        );
    }

    @Operation(summary = "Delete a product by ID")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        null,
                        "Product deleted successfully"
                )
        );
    }


}
