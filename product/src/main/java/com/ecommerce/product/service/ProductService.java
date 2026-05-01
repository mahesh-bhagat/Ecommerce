package com.ecommerce.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.ProductRequestDTO;
import com.ecommerce.product.dto.ProductResponseDTO;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // CREATE
    public ProductResponseDTO addProduct(ProductRequestDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        Product saved = repo.save(product);
        return ProductMapper.toDTO(saved);
    }

    // GET ALL
    public List<ProductResponseDTO> getAllProducts() {
        return repo.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    // GET BY ID
    public ProductResponseDTO getProductById(Long id) {
        Product p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductMapper.toDTO(p);
    }

    // GET BY NAME
    public List<ProductResponseDTO> getByName(String name) {
        return repo.findByName(name)
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    // GET BY CATEGORY + OPTIONAL RATING FILTER
    public List<ProductResponseDTO> getByCategoryAndRating(String category, Double rating) {

        List<Product> products;

        if (rating != null) {
            products = repo.findByCategoryAndRatingGreaterThanEqual(category, rating);
        } else {
            products = repo.findByCategory(category);
        }

        return products.stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    // UPDATE
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {

        Product existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(dto.getName());
        existing.setCategory(dto.getCategory());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setRating(dto.getRating());

        Product updated = repo.save(existing);
        return ProductMapper.toDTO(updated);
    }

    // DELETE
    public void deleteProduct(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        repo.deleteById(id);
    }
}