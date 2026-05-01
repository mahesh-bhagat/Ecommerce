package com.ecommerce.product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.dto.ProductRequestDTO;
import com.ecommerce.product.dto.ProductResponseDTO;
import com.ecommerce.product.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;
    
    // Inject server port to identify which instance handled the request
    @Value("${server.port}")
    private int serverPort;

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

    // GET BY ID - Returns instance info for load balancing demo
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        ProductResponseDTO product = service.getProductById(id);
        // We could add instance info here if we had a custom DTO
        return ResponseEntity.ok(product);
    }
    
    // INSTANCE INFO - Returns which instance handled the request
    @GetMapping("/instance-info")
    public ResponseEntity<Map<String, String>> getInstanceInfo() {
        return ResponseEntity.ok(Map.of(
            "serviceName", "product-service",
            "instancePort", String.valueOf(serverPort),
            "message", "This response from instance running on port " + serverPort
        ));
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
