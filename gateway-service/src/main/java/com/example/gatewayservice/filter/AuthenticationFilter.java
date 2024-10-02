package com.example.gatewayservice.filter;

import com.example.gatewayservice.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    JwtUtils jwtUtils;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            System.out.println("_________________________________");
            System.out.println(routeValidator.isSecured.test(exchange.getRequest()));

            System.out.println("_________________________________");
            if(routeValidator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey("Authorization")){
                    throw new RuntimeException("Authorization header not present");
                }

                String authHeader = exchange.getRequest().getHeaders().get("Authorization").get(0);
                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);

                }
                try {
                    //restTemplate.getForObject("http://AUTH-SERVICE/auth/validate",String.class);
                    jwtUtils.validateToken(authHeader);
                }catch (Exception e){
                    e.printStackTrace();
                    throw new RuntimeException("invalide acees");
                }

            }
            return chain.filter(exchange);
        }));
    }

    public static class Config {

    }
}
