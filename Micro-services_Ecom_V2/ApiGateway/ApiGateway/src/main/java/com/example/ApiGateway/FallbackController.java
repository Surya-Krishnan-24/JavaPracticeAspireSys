package com.example.ApiGateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class FallbackController {

    @GetMapping("/fallback/products")
    public ResponseEntity<List<String>> productsFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Product service is unavailable, please try again later."));
    }

    @GetMapping("/fallback/user")
    public ResponseEntity<List<String>> userFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("User service is unavailable, please try again later."));
    }

    @GetMapping("/fallback/order")
    public ResponseEntity<List<String>> orderFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Order service is unavailable, please try again later."));
    }

    @GetMapping("/fallback/admin")
    public ResponseEntity<List<String>> adminFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Admin service is unavailable, please try again later."));
    }

    @GetMapping("/fallback/seller")
    public ResponseEntity<List<String>> sellerFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Seller service is unavailable, please try again later."));
    }
}
