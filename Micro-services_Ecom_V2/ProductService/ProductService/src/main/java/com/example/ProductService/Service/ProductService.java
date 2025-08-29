package com.example.ProductService.Service;

import com.example.ProductService.DOA.ProductRepo;
import com.example.ProductService.DTO.ProductAddedEvent;
import com.example.ProductService.DTO.ProductQuantityRequest;
import com.example.ProductService.DTO.ProductRequest;
import com.example.ProductService.DTO.ProductResponse;
import com.example.ProductService.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final StreamBridge streamBridge;


    public List<ProductResponse> getAllProducts() {
        return productRepo.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getAllProductsBySeller(String sellerName) {
        return productRepo.findByActiveTrue().stream()
                .filter(product -> sellerName != null && sellerName.equals(product.getSeller()))
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse createProduct(ProductRequest productRequest, String email) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct = productRepo.save(product);
        ProductAddedEvent productAddedEvent1 = new ProductAddedEvent();
        ProductAddedEvent productAddedEvent = mapToProductEvent(savedProduct,productAddedEvent1);
        productAddedEvent.setSellerEmail(email);
        streamBridge.send("createProduct-out-0", productAddedEvent);

        return mapToProductResponse(savedProduct);
    }

    private ProductAddedEvent mapToProductEvent(Product savedProduct, ProductAddedEvent productAddedEvent1) {
        productAddedEvent1.setId(savedProduct.getId());
        productAddedEvent1.setName(savedProduct.getName());
        productAddedEvent1.setActive(savedProduct.getActive());
        productAddedEvent1.setDescription(savedProduct.getDescription());
        productAddedEvent1.setCategory(savedProduct.getCategory());
        productAddedEvent1.setPrice(savedProduct.getPrice());
        productAddedEvent1.setSeller(savedProduct.getSeller());
        productAddedEvent1.setImageUrl(savedProduct.getImageUrl());
        productAddedEvent1.setStockQuantity(savedProduct.getStockQuantity());
        productAddedEvent1.setCreatedAt(savedProduct.getCreatedAt());
        productAddedEvent1.setUpdatedAt(savedProduct.getUpdatedAt());
        return productAddedEvent1;
    }

    public Optional<ProductResponse> getProductById(int id) {
        return productRepo.findByIdAndActiveTrue(id)
                .map(this::mapToProductResponse);
    }

    public Optional<ProductResponse> getProductByIdOfSeller(int id, String seller) {
        return productRepo.findById(id)
                .filter(product -> seller != null && seller.equals(product.getSeller()))
                .map(this::mapToProductResponse);
    }

    public ProductResponse updateProductBySeller(Authentication authentication, int id, String sellerName, ProductRequest productRequest) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isEmpty()) return null;

        Product product = optionalProduct.get();

        boolean isAdmin = hasRole(authentication, "ROLE_ADMIN");
        boolean isSeller = hasRole(authentication, "ROLE_SELLER");

        if (isSeller && (product.getSeller() == null || !product.getSeller().equals(sellerName))) return null;
        if (!isAdmin && !isSeller) return null;

        updateProductFromRequest(product, productRequest);
        Product savedProduct = productRepo.save(product);
        return mapToProductResponse(savedProduct);
    }

    public boolean deleteProduct(int id) {
        return productRepo.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepo.save(product);
                    return true;
                }).orElse(false);
    }

    public boolean deleteProductBySeller(int id, String seller) {
        System.out.println(seller);
        return productRepo.findById(id)
                .filter(product -> seller != null && seller.equals(product.getSeller()))
                .map(product -> {
                    product.setActive(false);
                    System.out.println(seller);
                    System.out.println(product.getSeller());
                    productRepo.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProduct(String keyword) {
        return productRepo.findByNameContainingOrDescriptionContaining(keyword, keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchProductBySeller(String keyword, String seller) {
        return productRepo.findByNameContainingOrDescriptionContaining(keyword, keyword).stream()
                .filter(product -> seller != null && seller.equals(product.getSeller()))
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public String updateProductQuantityBySeller(String sellerName, List<ProductQuantityRequest> productQuantityRequests) {
        boolean updated = false;

        for (ProductQuantityRequest pqr : productQuantityRequests) {
            Optional<Product> optionalProduct = productRepo.findById(pqr.getProductId());

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();


                if (product.getSeller() == null || !product.getSeller().equalsIgnoreCase(sellerName)) {
                    continue;
                }

                int newQuantity = pqr.getStockQuantity();
                product.setStockQuantity(newQuantity);
                product.setActive(newQuantity > 0);

                productRepo.save(product);
                updated = true;
            }
        }

        return updated ? "Updated" : "NoMatchingProducts";
    }


    public String updateProductQuantity(List<ProductQuantityRequest> productQuantityRequests) {
        boolean updated = false;

        for (ProductQuantityRequest pqr : productQuantityRequests) {
            Optional<Product> optionalProduct = productRepo.findById(pqr.getProductId());

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                int newQuantity = product.getStockQuantity() - pqr.getStockQuantity();
                product.setStockQuantity(newQuantity);
                product.setActive(newQuantity > 0);

                productRepo.save(product);
                updated = true;
            }
        }

        return updated ? "Updated" : "NoMatchingProducts";
    }




    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setSeller(productRequest.getSeller());
        product.setActive(productRequest.getStockQuantity() > 0);
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
        productResponse.setSeller(savedProduct.getSeller());
        return productResponse;
    }

    private boolean hasRole(Authentication auth, String role) {
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals(role));
    }
}
