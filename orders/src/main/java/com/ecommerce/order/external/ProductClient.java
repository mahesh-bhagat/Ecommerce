package com.ecommerce.order.external;

import com.ecommerce.order.external.dto.ProductResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProductClient {

    private final WebClient.Builder webClientBuilder;

    public ProductClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public ProductResponseDTO getProduct(Long productId) {

        System.out.println("Calling PRODUCT-SERVICE...");

        return webClientBuilder.build()
                .get()
                .uri("http://PRODUCT-SERVICE/products/" + productId)
                .retrieve()
                .bodyToMono(ProductResponseDTO.class)
                .block();
    }
}