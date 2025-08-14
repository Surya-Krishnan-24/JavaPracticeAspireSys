package com.example.ProductService.DOA;

import aj.org.objectweb.asm.commons.Remapper;
import com.example.ProductService.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    List<Product> findByActiveTrue();

    List<Product> findByNameContainingOrDescriptionContaining(String name,String description);

    Optional<Product> findByIdAndActiveTrue(int id);
}
