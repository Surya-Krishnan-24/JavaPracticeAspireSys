package com.example.SpringEcom.Service;

import com.example.SpringEcom.Model.Product;
import com.example.SpringEcom.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        List<Product> products = productRepo.findAll();
        products.sort(Comparator.comparingInt(Product::getId ));
        return products;
    }

    public Product getProduct(int id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product addorupdateProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return productRepo.save(product);
    }


    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    public List<Product> searchProduct(String keyword) {
        List<Product> products = productRepo.findByNameContainingIgnoreCase(keyword);
        products.sort(Comparator.comparingInt(Product::getId));
        return products;
    }
}
