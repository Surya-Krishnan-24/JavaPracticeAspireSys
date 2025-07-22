package com.example.SpringEcom.Controller;

import com.example.SpringEcom.Model.Product;
import com.example.SpringEcom.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(name = "id") int id){
        Product product = productService.getProduct(id);

        if(product != null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id){
        Product product = productService.getProduct(id);
        if(product != null) {
            return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile image){
        Product savedProduct = null;
        try {
            savedProduct = productService.addorupdateProduct(product,image);
            return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id,@RequestPart Product product, @RequestPart MultipartFile image){
        try {
            Product response = productService.addorupdateProduct(product, image);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        catch (IOException e){
            return new ResponseEntity<>(product,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product = productService.getProduct(id);
        if(product!=null){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchByKeyword(@RequestParam String keyword){
        List<Product> products = productService.searchProduct(keyword);
        System.out.println("searching with " + keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);

    }
}
