package org.example.test.Service;

import org.example.test.DOA.OrderRepo;
import org.example.test.DOA.UserRepo;
import org.example.test.DTO.CartItemResponse;
import org.example.test.DTO.OrderItemDTO;
import org.example.test.DTO.OrderResponse;
import org.example.test.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    public OrderResponse createOrder(String userId) {
        List<CartItemResponse> cartItemResponses = cartService.getAllProductFromCart(userId);
        if(cartItemResponses.isEmpty()){
            return null;
        }
        Optional<User> user =userRepo.findById(Integer.parseInt(userId));

        if (user.isEmpty()){
            return null;
        }
        User actualUser = user.get();


        BigDecimal totalPrice = cartItemResponses.stream()
                .map(CartItemResponse::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(actualUser);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItemResponses.stream()
                .map(cartItemResponse -> {
            OrderItem orderItem = new OrderItem();
            Product product = new Product();

            product.setId(cartItemResponse.getProduct().getId());
            product.setActive(cartItemResponse.getProduct().getActive());
            product.setPrice(cartItemResponse.getProduct().getPrice());
            product.setName(cartItemResponse.getProduct().getName());
            product.setCategory(cartItemResponse.getProduct().getCategory());
            product.setDescription(cartItemResponse.getProduct().getDescription());
            product.setImageUrl(cartItemResponse.getProduct().getImageUrl());
            product.setStockQuantity(cartItemResponse.getProduct().getStockQuantity());


            orderItem.setProduct(product);
            orderItem.setQuantity(cartItemResponse.getQuantity());
            orderItem.setPrice(cartItemResponse.getPrice());
            orderItem.setOrder(order);

            return orderItem;
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        order.setStatus(OrderStatus.CONFIRMED);

        Order savedOrder = orderRepo.save(order);

        cartService.clearCart(userId);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(savedOrder.getOrder_id());
        orderResponse.setTotalAmount(savedOrder.getTotalAmount());
        orderResponse.setStatus(savedOrder.getStatus());
        orderResponse.setCreatedAt(savedOrder.getCreatedAt());

        List<OrderItemDTO> itemDTOs = savedOrder.getItems().stream()
                .map(item -> {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setId(item.getId());
            dto.setProductid(item.getProduct().getId());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());
            return dto;
        }).collect(Collectors.toList());

        orderResponse.setItems(itemDTOs);

        return orderResponse;

    }
}
