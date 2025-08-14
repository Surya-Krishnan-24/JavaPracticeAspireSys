package com.example.OrderService.Service;


import com.example.OrderService.Clients.UserServiceClient;
import com.example.OrderService.DOA.OrderRepo;
import com.example.OrderService.DTO.OrderItemDTO;
import com.example.OrderService.DTO.OrderResponse;
import com.example.OrderService.DTO.UserResponse;
import com.example.OrderService.Model.CartItem;
import com.example.OrderService.Model.Order;
import com.example.OrderService.Model.OrderItem;
import com.example.OrderService.Model.OrderStatus;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final CartService cartService;

    private final OrderRepo orderRepo;

    private final UserServiceClient userServiceClient;

    public Optional<OrderResponse> createOrder(String userId) {

        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }


        UserResponse user = userServiceClient.getUserDetails(userId);
        if (user == null) {
            return Optional.empty();
        }


        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        0,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .toList();

        order.setItems(orderItems);
        Order savedOrder = orderRepo.save(order);


        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getOrder_id(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProductId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice()
                        ))
                        .toList(),
                order.getCreatedAt()
        );
    }
}