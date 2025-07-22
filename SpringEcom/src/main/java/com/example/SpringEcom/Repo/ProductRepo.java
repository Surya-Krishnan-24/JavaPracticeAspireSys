package com.example.SpringEcom.Repo;

import com.example.SpringEcom.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
    public List<Product> findByNameContainingIgnoreCase(String keyword);

}
