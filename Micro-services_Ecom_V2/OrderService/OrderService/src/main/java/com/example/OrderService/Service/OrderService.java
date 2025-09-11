package com.example.OrderService.Service;


import com.example.OrderService.Clients.ProductServiceClient;
import com.example.OrderService.Clients.UserServiceClient;
import com.example.OrderService.GlobalExceptionHandler.ResourceNotFoundException;
import org.springframework.cloud.stream.function.StreamBridge;
import com.example.OrderService.DOA.OrderRepo;
import com.example.OrderService.DTO.*;
import com.example.OrderService.DTO.UserResponse;
import com.example.OrderService.Model.CartItem;
import com.example.OrderService.Model.Order;
import com.example.OrderService.Model.OrderItem;
import com.example.OrderService.Model.OrderStatus;
import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

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
            throw new ResourceNotFoundException("Cart is empty. Cannot place an order.");
        }

        UserResponse user = userServiceClient.getUser(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found for ID: " + userId);
        }


        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING);
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

    public String getUserId(Jwt jwt) {
        String keycloakId = jwt.getSubject();
        return userServiceClient.getUserDetailsByKeycloak(keycloakId);
    }

    public List<OrderResponse> getOrder(String userId) {
        return orderRepo.findByUserId(userId).stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    public List<OrderResponseSeller> getOrdersForSeller(String sellerName) {
        List<Order> allOrders = orderRepo.findAll();
        List<OrderResponseSeller> responseList = new ArrayList<>();

        for (Order order : allOrders) {
            List<OrderItem> sellerItems = new ArrayList<>();

            for (OrderItem item : order.getItems()) {
                Product product = productServiceClient.getProductByIdSeller(item.getProductId());
                if (product != null && sellerName.equals(product.getSeller())) {
                    sellerItems.add(item);
                }
            }

            if (!sellerItems.isEmpty()) {
                OrderResponseSeller response = new OrderResponseSeller();
                response.setOrderID(order.getOrder_id());
                response.setOrderStatus(String.valueOf(order.getStatus()));


                List<ProductsOrderResponse> productResponses = sellerItems.stream()
                        .map(item -> {
                            Product product = productServiceClient.getProductByIdSeller(item.getProductId());

                            ProductsOrderResponse p = new ProductsOrderResponse();
                            p.setProductId(item.getProductId());
                            p.setProductName(product.getName());
                            p.setQuantity(item.getQuantity());
                            p.setPrice(item.getPrice().intValue());
                            return p;
                        }).collect(Collectors.toList());

                response.setProductsOrderResponses(productResponses);

                response.setName(userServiceClient.getUsername(order.getUserId()));

                UserAddress userAddress = userServiceClient.getUserAddress(order.getUserId());
                if (userAddress!= null) {
                    response.setAddressResponse(userAddress);
                }

                responseList.add(response);
            }
        }

        return responseList.stream().sorted((a, b) -> a.getOrderID() - b.getOrderID()).toList();
    }

    public String updateOrderStatus(int id, OrderStatus status) {
        Optional<Order> order = orderRepo.findById(id);
        order.get().setStatus(status);
        orderRepo.save(order.get());
        OrderUpdatedEvent orderUpdatedEvent = new OrderUpdatedEvent();
        orderUpdatedEvent.setOrderId(order.get().getOrder_id());

        List<String> productnames = new ArrayList<>();
        for(OrderItem p : order.get().getItems()) {
            Product product = productServiceClient.getProductByIdSeller(p.getProductId());
            productnames.add(product.getName());
        }
        orderUpdatedEvent.setProductNames(productnames);
        orderUpdatedEvent.setOrderStatus(status);

        UserResponse userResponse = userServiceClient.getUser(order.get().getUserId());
        orderUpdatedEvent.setUserEmail(userResponse.getEmail());
        orderUpdatedEvent.setUserName(userResponse.getFirstName());
        streamBridge.send("orderUpdated-out-0", orderUpdatedEvent);
        return "UPDATED";
    }
}