# API Gateway for E-commerce Microservices

## Overview

This is the **API Gateway** module for the E-commerce project. It provides a single entry point for all microservices and routes requests to the appropriate services based on URL patterns.

## Features

- **Single Entry Point**: All API requests go through the Gateway on port 8083
- **Dynamic Service Discovery**: Uses Eureka to discover registered services
- **Load Balancing**: Distributes requests across multiple service instances
- **Route Management**: Routes based on URL path patterns

## Architecture

```
                                ┌─────────────────┐
                                │   API Gateway   │
                                │    (Port 8083)  │
                                └────────┬────────┘
                                         │
           ┌──────────────────────────────┼──────────────────────────────┐
           │                              │                              │
           ▼                              ▼                              ▼
    ┌──────────────┐             ┌──────────────┐              ┌──────────────┐
    │ User Service │             │Product Service              │ Order Service│
    │  (Port 8081) │             │  (Port 8080) │              │  (Port 8082) │
    └──────────────┘             └──────────────┘              └──────────────┘
           │                              │                              │
           └──────────────────────────────┼──────────────────────────────┘
                                         │
                                ┌────────┴────────┐
                                │   Eureka Server │
                                │   (Port 8761)   │
                                └─────────────────┘
```

## Service URLs

All services can be accessed through the API Gateway:

| Service        | Direct URL                    | Gateway URL                    |
|---------------|-------------------------------|--------------------------------|
| Product       | `http://localhost:8080/`       | `http://localhost:8083/product/`|
| User          | `http://localhost:8081/`       | `http://localhost:8083/user/`   |
| Order         | `http://localhost:8082/`      | `http://localhost:8083/order/`|
| Eureka Dash  | `http://localhost:8761/`       | -                              |

## How to Run

### Prerequisites

1. Start **Eureka Server** first (Port 8761)
2. Start microservices (they will register with Eureka)
3. Then start the **API Gateway**

### Running the Gateway

```bash
cd api-gateway
mvn spring-boot:run
```

Or build and run:

```bash
cd api-gateway
mvn clean package
java -jar target/api-gateway-0.0.1-SNAPSHOT.jar
```

### Service Startup Order

1. **Eureka Server**: `eureka/` - Port 8761
2. **Product Service**: `product/` - Port 8080
3. **User Service**: `user/` - Port 8081
4. **Order Service**: `orders/` - Port 8082
5. **API Gateway**: `api-gateway/` - Port 8083

## Testing

### Example Requests via Gateway

#### Get All Products
```bash
curl http://localhost:8083/product/products
```

#### Get Product by ID
```bash
curl http://localhost:8083/product/products/1
```

#### Get All Users
```bash
curl http://localhost:8083/user/users
```

#### Create Order
```bash
curl -X POST http://localhost:8083/order/orders \
  -H "Content-Type: application/json" \
  -d '{"userId": 1, "productId": 1, "quantity": 2}'
```

## Configuration

### Port Configuration
- **API Gateway Port**: 8083 (configurable in `application.properties`)

### Routing Configuration

The routing is defined in `GatewayConfig.java`:

```java
.route("product-service", r -> r
    .path("/product/**")
    .uri("lb://product-service"))
```

### Eureka Integration

The Gateway registers with Eureka to discover services:
```properties
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

## Troubleshooting

### Service Not Found
- Ensure Eureka Server is running first
- Check that the target service is running and registered with Eureka

### Connection Refused
- Verify all services are started
- Check ports are not in use

### 404 Not Found
- Verify the correct path is used (e.g., `/product/products`)
- Check the service is running

## Swagger UI

Each service has its own Swagger UI accessible directly:

- Product Service: `http://localhost:8080/swagger-ui.html`
- User Service: `http://localhost:8081/swagger-ui.html`
- Order Service: `http://localhost:8082/swagger-ui.html`

## Notes

- The Gateway uses Spring Cloud LoadBalancer for client-side load balancing
- Each service registers itself with Eureka automatically
- The Gateway looks up services dynamically from Eureka
