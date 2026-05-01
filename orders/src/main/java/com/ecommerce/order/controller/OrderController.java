package com.ecommerce.order.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.order.dto.OrderRequestDTO;
import com.ecommerce.order.dto.OrderResponseDTO;
import com.ecommerce.order.external.ProductClient;
import com.ecommerce.order.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;
    private final ProductClient productClient;

    public OrderController(OrderService service, ProductClient productClient) {
        this.service = service;
        this.productClient = productClient;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createOrder(dto));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllOrders());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    
    // TEST LOAD BALANCING - Get which Product Service instance handled the request
    @GetMapping("/test-load-balancing")
    public ResponseEntity<Map<String, String>> testLoadBalancing() {
        Map<String, String> instanceInfo = productClient.getInstanceInfo();
        return ResponseEntity.ok(instanceInfo);
    }
}
