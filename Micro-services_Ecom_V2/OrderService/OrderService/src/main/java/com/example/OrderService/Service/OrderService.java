package com.example.OrderService.Service;


import com.example.OrderService.Clients.ProductServiceClient;
import com.example.OrderService.Clients.UserServiceClient;
import org.springframework.cloud.stream.function.StreamBridge;
import com.example.OrderService.DOA.OrderRepo;
import com.example.OrderService.DTO.*;
import com.example.OrderService.Model.CartItem;
import com.example.OrderService.Model.Order;
import com.example.OrderService.Model.OrderItem;
import com.example.OrderService.Model.OrderStatus;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final CartService cartService;

    private final OrderRepo orderRepo;

    private final UserServiceClient userServiceClient;

    private final ProductServiceClient productServiceClient;

    private final StreamBridge streamBridge;

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

        List<ProductQuantityRequest> productQuantityRequests = cartItems.stream().map(items -> new ProductQuantityRequest(
                items.getProductId(),
                items.getQuantity()
        )).toList();


        order.setItems(orderItems);
        Order savedOrder = orderRepo.save(order);



        cartService.clearCart(userId);

        productServiceClient.updateProductQuantity(productQuantityRequests);
        List<NotificationOrderResponse> notificationOrderResponses = new ArrayList<>();

        for (CartItem item : cartItems) {
            ProductResponse product = productServiceClient.getProductDetails(item.getProductId());
            NotificationOrderResponse notificationOrderResponse = new NotificationOrderResponse();
            notificationOrderResponse.setName(product.getName());
            int q = cartItems.stream().filter(i -> i.getProductId() == product.getId()).map(CartItem::getQuantity).reduce(0, Integer::sum);
            notificationOrderResponse.setQuantity(q);
            notificationOrderResponse.setPrice(product.getPrice());
            notificationOrderResponse.setTotal(product.getPrice().multiply(BigDecimal.valueOf(q)));
            if (notificationOrderResponse != null) {
                notificationOrderResponses.add(notificationOrderResponse);
            }
        }


        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(
                savedOrder.getOrder_id(),
                user.getFirstName(),
                user.getEmail(),
                savedOrder.getStatus(),
                notificationOrderResponses,
                savedOrder.getTotalAmount(),
                savedOrder.getCreatedAt()
        );

       streamBridge.send("createOrder-out-0", orderCreatedEvent);


        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private List<OrderItemDTO> mapToOrderItemDTOs(List<OrderItem> items){
        return items.stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getPrice().multiply(new BigDecimal(item.getQuantity()))


                        )).toList();
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
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        ))
                        .toList(),
                order.getCreatedAt()
        );
    }
}