package com.ecommerce.product.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.ecommerce.product.dto.ProductRequestDTO;
import com.ecommerce.product.dto.ProductResponseDTO;
import com.ecommerce.product.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Void> add1(
            @Valid @RequestBody ProductRequestDTO dto) {

        service.addProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    // GET BY NAME
    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductResponseDTO>> getByName(
            @PathVariable String name) {
        return ResponseEntity.ok(service.getByName(name));
    }

    // GET BY CATEGORY + OPTIONAL RATING
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDTO>> getByCategory(
            @PathVariable String category,
            @RequestParam(required = false) Double rating) {

        return ResponseEntity.ok(
                service.getByCategoryAndRating(category, rating)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO dto) {

        return ResponseEntity.ok(service.updateProduct(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}