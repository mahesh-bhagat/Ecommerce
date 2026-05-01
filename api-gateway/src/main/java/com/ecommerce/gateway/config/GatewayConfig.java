package com.ecommerce.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gateway Route Configuration
 * 
 * This configuration defines explicit routing rules for the API Gateway.
 * Each route maps a URL path pattern to a specific microservice.
 * 
 * Routes:
 * - /product/** -> product-service (Product microservice)
 * - /user/**    -> user-service    (User microservice)
 * - /order/**   -> order-service  (Order microservice)
 * 
 * The "lb://" prefix tells Spring Cloud Gateway to use Load Balancing
 * and look up the service from Eureka.
 */
@Configuration
public class GatewayConfig {

    /**
     * Define custom route locator with explicit routes
     * 
     * @param builder RouteLocatorBuilder to build routes
     * @return RouteLocator with all configured routes
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                
                // Product Service Routes
                // All /product/** requests go to product-service
                .route("product-service", r -> r
                        .path("/product/**")
                        .filters(f -> f
                                .stripPrefix(0)) // Keep the original path
                        .uri("lb://product-service"))
                
                // User Service Routes
                // All /user/** requests go to user-service
                .route("user-service", r -> r
                        .path("/user/**")
                        .filters(f -> f
                                .stripPrefix(0))
                        .uri("lb://user-service"))
                
                // Order Service Routes
                // All /order/** requests go to order-service
                .route("order-service", r -> r
                        .path("/order/**")
                        .filters(f -> f
                                .stripPrefix(0))
                        .uri("lb://order-service"))
                
                .build();
    }
}
