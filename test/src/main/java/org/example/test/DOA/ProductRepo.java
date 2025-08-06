package org.example.test.DOA;

import org.example.test.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    List<Product> findByActiveTrue();

    List<Product> findByNameContainingOrDescriptionContaining(String name,String description);
}
