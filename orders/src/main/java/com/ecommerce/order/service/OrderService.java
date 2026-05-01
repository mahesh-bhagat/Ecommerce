package com.ecommerce.order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.order.dto.OrderRequestDTO;
import com.ecommerce.order.dto.OrderResponseDTO;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.external.ProductClient;
import com.ecommerce.order.external.dto.ProductResponseDTO;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductClient productClient;

    public OrderService(OrderRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    // CREATE ORDER
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        // Fetch product details to get the price
        ProductResponseDTO product = productClient.getProduct(dto.getProductId());
        
        // Calculate total price: product price * quantity
        Double totalPrice = product.getPrice() * dto.getQuantity();
        
        Order order = OrderMapper.toEntity(dto);
        order.setTotalPrice(totalPrice);
        
        Order saved = repository.save(order);
        return OrderMapper.toDTO(saved);
    }

    // GET ALL
    public List<OrderResponseDTO> getAllOrders() {
        return repository.findAll()
                .stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public OrderResponseDTO getOrderById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderMapper.toDTO(order);
    }

    // DELETE
    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}