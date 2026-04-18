package com.school.project.service;

import com.school.project.dto.ProductFilterDTO;
import com.school.project.dto.ProductPatchDTO;
import com.school.project.specifications.ProductSpecification;
import com.school.project.dto.ProductRequestDTO;
import com.school.project.dto.ProductResponseDTO;
import com.school.project.mapper.ProductMapper;
import com.school.project.models.Product;
import com.school.project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;


    public Page<ProductResponseDTO> getAllProducts(ProductFilterDTO filter, Pageable pageable) {

        log.info("Getting products from filter: name={}, minPrice={}, maxPrice={}",
                filter.getName(),filter.getMinPrice(),filter.getMaxPrice());

        Specification<Product> spec = Specification.where(ProductSpecification.nameContains(filter.getName()))
                .and(ProductSpecification.priceGreaterThan(filter.getMinPrice())
                        .and(ProductSpecification.priceLessThan(filter.getMaxPrice())));

        return productRepository.findAll(spec, pageable)
                .map(mapper::toResponse);

    }

    public ProductResponseDTO getProductById(Long id) {

        log.info("Getting product by ID={}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapper.toResponse(product);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product id={} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
                });
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        log.info("Created product: {}", dto.getName());

        Product product = mapper.toEntity(dto);
        Product saved = productRepository.save(product);

        log.info("Product sucessfully created: {}", saved.getId());

        return mapper.toResponse(saved);
    }

    @Transactional
    public List<ProductResponseDTO> createBatch(List<ProductRequestDTO> dtos) {
        List<Product> products = dtos.stream()
                .map(mapper::toEntity)
                .toList();

        List<Product> saved = productRepository.saveAll(products);

        return saved.stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ProductResponseDTO updateProduct(Long id , ProductRequestDTO dto) {

        log.info("Updating ID product: {}", id);

        Product existingProduct = findProductById(id); // Aqui busca um objeto que tenha o mesmo id usando o metodo aqui de cima

        mapper.updateEntity(dto, existingProduct); //Aqui passa o request e o objeto localizado para atualização do objeto

        Product updatedProduct = productRepository.save(existingProduct); //Aqui salva as atualizações no banco

        log.info("Product ID sucessfully updated: {}", updatedProduct.getId());

        return mapper.toResponse(updatedProduct); //Aqui retorna o objeto atualizado em formado response
    }

    public ProductResponseDTO patchProduct(Long id, ProductPatchDTO dto) {

        log.info("Patched ID product: {}", id);

        Product existingProduct = findProductById(id);

        mapper.PatchEntity(dto, existingProduct);

        Product patchProduct = productRepository.save(existingProduct);

        log.info("Product ID sucessfully patched: {}", patchProduct.getId());

        return mapper.toResponse(patchProduct);
    }

    public void deleteProduct(Long id) {

        log.warn("Deleting product by ID: {}", id);

        Product existingProduct = findProductById(id);

        productRepository.delete(existingProduct);

        log.info("Product ID sucessfully deleted: {}", id);
    }


}
