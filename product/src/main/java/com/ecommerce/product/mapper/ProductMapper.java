package com.ecommerce.product.mapper;

import com.ecommerce.product.dto.*;
import com.ecommerce.product.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {
        Product p = new Product();
        p.setName(dto.getName());
        p.setCategory(dto.getCategory());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setRating(dto.getRating());
        return p;
    }

    public static ProductResponseDTO toDTO(Product p) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setProductId(p.getProductId());
        dto.setName(p.getName());
        dto.setCategory(p.getCategory());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        dto.setRating(p.getRating());
        return dto;
    }
}