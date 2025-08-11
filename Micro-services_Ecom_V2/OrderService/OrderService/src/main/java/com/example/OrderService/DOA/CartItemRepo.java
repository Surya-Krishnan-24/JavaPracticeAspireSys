package com.example.OrderService.DOA;

import com.example.OrderService.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Integer> {

    CartItem findByUserIdAndProductId(int userId, int productId);

    List<CartItem> findByUserId(int userId);

    void deleteByUserId(int userId);
}
