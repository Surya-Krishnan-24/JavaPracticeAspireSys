package com.example.ApiGateway.Security;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http.csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange ->
                        exchange.pathMatchers("/actuator/**").permitAll()
                                .pathMatchers("/products/**").permitAll()
                                .pathMatchers("/orders/**").hasRole("USER")
                                .pathMatchers("/cart/**").hasRole("USER")
                                .pathMatchers("/seller/**").permitAll()
                                .pathMatchers("/users/**").permitAll()
                                .pathMatchers("/eureka/**").permitAll()
                                .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->

                        jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor())))
                .build();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r.path("/products/**")
                        .filters(f -> f
                                .rewritePath("/products/(?<segment>.*)", "/api/products/${segment}")
                                .circuitBreaker(c -> c
                                        .setName("productBreaker")
                                        .setFallbackUri("forward:/fallback/products")))
                        .uri("lb://product-service"))

                .route("user-service", r -> r.path("/users/**")
                        .filters(f -> f
                                .rewritePath("/users/(?<segment>.*)", "/api/users/${segment}")
                                .circuitBreaker(c -> c
                                        .setName("userBreaker")
                                        .setFallbackUri("forward:/fallback/user")))
                        .uri("lb://user-service"))

                .route("order-service", r -> r.path("/orders/**", "/cart/**", "/cart")
                        .filters(f -> f.circuitBreaker(c -> c
                                .setName("orderBreaker")
                                .setFallbackUri("forward:/fallback/order")))
                        .uri("lb://order-service"))

                .route("admin-service", r -> r.path("/admin/**")
                        .filters(f -> f.circuitBreaker(c -> c
                                .setName("adminBreaker")
                                .setFallbackUri("forward:/fallback/admin")))
                        .uri("lb://admin-service"))

                .route("seller-service", r -> r.path("/seller/**")
                        .filters(f -> f.circuitBreaker(c -> c
                                .setName("sellerBreaker")
                                .setFallbackUri("forward:/fallback/seller")))
                        .uri("lb://seller-service"))

                .route("eureka-server-main", r -> r.path("/eureka/main")
                        .filters(f -> f.setPath("/"))
                        .uri("http://localhost:8761"))

                .route("eureka-server-static", r -> r.path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }



    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor(){

        ReactiveJwtAuthenticationConverter jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {

            List<String> roles = jwt.getClaimAsMap("resource_access")
                    .entrySet().stream()
                    .filter(entry -> entry.getKey().equals("Oauth2"))
                    .flatMap(entry -> ((Map<String, List<String>>) entry.getValue())
                            .get("roles").stream()).toList();
            System.out.println("Extracted role : "+ roles);

            return Flux.fromIterable(roles)
                    .map(role -> new SimpleGrantedAuthority("ROLE_"+ role));
        });
        return jwtAuthenticationConverter;
    }

}
