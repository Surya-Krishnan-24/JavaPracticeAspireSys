package com.example.ProductService.Service;

import com.example.ProductService.DOA.ProductRepo;
import com.example.ProductService.DTO.ProductQuantityRequest;
import com.example.ProductService.DTO.ProductRequest;
import com.example.ProductService.DTO.ProductResponse;
import com.example.ProductService.Model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {


    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> productResponses = productRepo.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());

        return productResponses;

    }

    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = new Product();

        updateProductFromRequest(product,productRequest);

        Product savedProduct = productRepo.save(product);

        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setCategory(savedProduct.getCategory());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setActive(savedProduct.getActive());
        productResponse.setImageUrl(savedProduct.getImageUrl());
        productResponse.setStockQuantity(savedProduct.getStockQuantity());

        return productResponse;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
        if(productRequest.getStockQuantity()<1) {
            product.setActive(false);
        }
        else {
            product.setActive(true);
        }
    }

    public ProductResponse updateProduct(int id,ProductRequest productRequest) {
        return productRepo.findById(id).map(existingproduct -> {
            updateProductFromRequest(existingproduct, productRequest);
            Product savedproduct = productRepo.save(existingproduct);
            return mapToProductResponse(savedproduct);
        }).orElse(null);
    }


    public boolean deleteProduct(int id) {
        return productRepo.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepo.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProduct(String keyword) {
        return productRepo.findByNameContainingOrDescriptionContaining(keyword,keyword)
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> getProductById(int id) {
        return productRepo.findByIdAndActiveTrue(id).map(this::mapToProductResponse);

    }

    public String updateProductQuantity(List<ProductQuantityRequest> productQuantityRequests) {

        for (ProductQuantityRequest pqr : productQuantityRequests) {
            Product product = productRepo.findById(pqr.getProductId()).get();

            product.setStockQuantity(product.getStockQuantity() - pqr.getStockQuantity());
            if(product.getStockQuantity() == 0){
                product.setActive(false);
            }

            productRepo.save(product);
        }

        return "Updated";

    }
}
