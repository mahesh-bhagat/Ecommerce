package com.ecommerce.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API Gateway Application
 * 
 * This is the entry point for the API Gateway service.
 * It routes requests to the appropriate microservices based on the URL path.
 * 
 * Features:
 * - Dynamic service discovery via Eureka
 * - Load balancing across service instances
 * - Centralized routing through a single port
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
