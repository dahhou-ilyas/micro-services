package com.example.gatewayservice;

import com.example.gatewayservice.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class GatewayServiceApplication {

    @Autowired
    AuthenticationFilter authenticationFilter;

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

//    @Bean
//    public DiscoveryClientRouteDefinitionLocator dynamicRoute(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
//        return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
//    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("inventory-service", r -> r
                        .path("/products/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://INVENTORY-SERVICE"))
                .route("customer-service", r -> r
                        .path("/customers/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://CUSTOMER-SERVICE"))
                .route("auth-service", r -> r
                        .path("/auth/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://AUTH-SERVICE"))
                .route("billing-servie", r -> r
                        .path("/auth/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://AUTH-SERVICE"))
                .build();
    }
}
