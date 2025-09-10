package com.example.ProductService.Controller;

import com.example.ProductService.DTO.ProductQuantityRequest;
import com.example.ProductService.DTO.ProductRequest;
import com.example.ProductService.DTO.ProductResponse;
import com.example.ProductService.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'USER')")
    @GetMapping("/seller/{sellerName}")
    public ResponseEntity<List<ProductResponse>> getAllProductsBySeller(@PathVariable String sellerName) {
        return new ResponseEntity<>(productService.getAllProductsBySeller(sellerName), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {
        Optional<ProductResponse> productResponse = Optional.ofNullable(productService.getProductById(id));
        return productResponse
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    @GetMapping("/seller/{id}/{sellerName}")
    public ResponseEntity<ProductResponse> getProductByIdOfSeller(@PathVariable int id, @PathVariable String sellerName) {
        System.out.println(sellerName+" "+id);
        Optional<ProductResponse> productResponse = Optional.ofNullable(productService.getProductByIdOfSeller(id, sellerName));
        return productResponse
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/seller/{sellerName}")
    public ResponseEntity<ProductResponse> createProduct(@PathVariable String sellerName, @RequestBody ProductRequest productRequest, @RequestHeader("email") String email) {
        productRequest.setSeller(sellerName);
        return new ResponseEntity<>(productService.createProduct(productRequest, email), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    @PutMapping("/{id}/seller/{sellerName}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable int id, @PathVariable String sellerName, @RequestBody ProductRequest productRequest, Authentication authentication) {
        ProductResponse productResponse = productService.updateProductBySeller(authentication, id, sellerName, productRequest);
        if (productResponse != null) {
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable int id) {
        boolean statusOfDeletion = productService.deleteProduct(id);
        return statusOfDeletion
                ? new ResponseEntity<>(statusOfDeletion, HttpStatus.OK)
                : new ResponseEntity<>(statusOfDeletion, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/seller/{id}/{seller}")
    public ResponseEntity<Boolean> deleteProductBySeller(@PathVariable int id, @PathVariable String seller) {
        boolean result = productService.deleteProductBySeller(id, seller);
        return ResponseEntity.ok(result);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/admin/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String keyword) {
        return new ResponseEntity<>(productService.searchProduct(keyword), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/search/seller/{seller}")
    public ResponseEntity<List<ProductResponse>> searchProductBySeller(@RequestParam String keyword, @PathVariable String seller) {
        return new ResponseEntity<>(productService.searchProductBySeller(keyword, seller), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    @PutMapping("/quantity/seller/{sellerName}")
    public ResponseEntity<String> updateProductQuantityBySeller(
            @PathVariable String sellerName,
            @RequestBody List<ProductQuantityRequest> productQuantityRequests) {

        String response = productService.updateProductQuantityBySeller(sellerName, productQuantityRequests);

        if ("Updated".equals(response)) {
            return ResponseEntity.ok("Quantity Updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No products matched the seller: " + sellerName);
        }
    }


    @PreAuthorize("hasRole('USER')")
    @PutMapping("/quantity")
    public ResponseEntity<String> updateProductQuantity(@RequestHeader(value = "X-Forwarded-By", required = false) String forwardedBy, @RequestBody List<ProductQuantityRequest> productQuantityRequests) {
        if(forwardedBy !=null) {
            if (forwardedBy.equals("OrderService")) {
                String response = productService.updateProductQuantity(productQuantityRequests);

                if ("Updated".equals(response)) {
                    return ResponseEntity.ok("Quantity Updated");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("No products matched ");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("USER cannot cannot modify product quantity ");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("USER cannot cannot modify product quantity ");
        }

    }
}
