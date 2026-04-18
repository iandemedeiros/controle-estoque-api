package com.school.project.specifications;

import com.school.project.models.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Product> priceGreaterThan(Double minPrice) {
        return (root, query, cb) -> {
            if (minPrice == null || minPrice < 0) return null;
            return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
        };
    }

    public static Specification<Product> priceLessThan(Double maxPrice) {
        return (root, query, cb) -> {
            if (maxPrice == null || maxPrice < 0) return null;
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }
}
