package com.ecommerce.order.external;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecommerce.order.external.dto.ProductResponseDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Component
public class ProductClient {

    private final WebClient.Builder webClientBuilder;

    public ProductClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "getProductFallback")
    @Retry(name = "productService")
    public ProductResponseDTO getProduct(Long productId) {

        System.out.println("Calling PRODUCT-SERVICE...");

        return webClientBuilder.build()
                .get()
                .uri("http://PRODUCT-SERVICE/products/" + productId)
                .retrieve()
                .bodyToMono(ProductResponseDTO.class)
                .block();
    }

    /**
     * Fallback method - called when Circuit Breaker is OPEN or service fails
     * This demonstrates resilience - the service continues working even when Product service is down!
     */
    public ProductResponseDTO getProductFallback(Long productId, Exception exception) {
        System.out.println("Circuit Breaker/Fallback triggered for productId: " + productId);
        System.out.println("Error: " + exception.getMessage());
        
        // Return a default product or handle gracefully
        ProductResponseDTO fallbackProduct = new ProductResponseDTO();
        fallbackProduct.setProductId(productId);
        fallbackProduct.setName("Product Service Unavailable");
        fallbackProduct.setPrice(0.0); // Default price when service is down
        return fallbackProduct;
    }
}
