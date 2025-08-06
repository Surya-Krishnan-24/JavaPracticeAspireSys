package org.example.test.DOA;

import org.example.test.Model.CartItem;
import org.example.test.Model.Product;
import org.example.test.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Integer> {

    CartItem findByUserAndProduct(User actualUser, Product actualProduct);

    void deleteByUserAndProduct(User actualUser, Product actualProduct);

    List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}
