package com.example.ProductService.Controller;

import com.example.ProductService.DTO.ProductQuantityRequest;
import com.example.ProductService.DTO.ProductRequest;
import com.example.ProductService.DTO.ProductResponse;
import com.example.ProductService.Service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {

        Optional<ProductResponse> productResponse = productService.getProductById(id);

        return productResponse.map(response -> new ResponseEntity<>(response, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable int id, @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.updateProduct(id, productRequest);
        if (productResponse != null) {
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable int id) {
        boolean statusOfDeletion = productService.deleteProduct(id);
        return statusOfDeletion
                ? new ResponseEntity<>(statusOfDeletion, HttpStatus.OK)
                : new ResponseEntity<>(statusOfDeletion,HttpStatus.NO_CONTENT);

    }


    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String keyword) {
        return new ResponseEntity<>(productService.searchProduct(keyword), HttpStatus.OK);


    }

    @PutMapping("/quantity")
    public ResponseEntity<String> updateProductQuantity(@RequestBody List<ProductQuantityRequest> productQuantityRequests) {

         String  response = productService.updateProductQuantity(productQuantityRequests);
        if (response.equals("Updated")) {
            return new ResponseEntity<>("Quantity Updated",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
