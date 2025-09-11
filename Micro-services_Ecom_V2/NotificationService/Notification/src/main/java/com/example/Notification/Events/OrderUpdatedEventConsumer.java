package com.example.Notification.Events;

import com.example.Notification.DTO.OrderUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OrderUpdatedEventConsumer {

    private final JavaMailSender mailSender;

    @Bean
    public Consumer<OrderUpdatedEvent> handleOrderUpdatedEvent() {
        return orderUpdatedEvent -> {

            SimpleMailMessage message = new SimpleMailMessage();
            String userEmail = orderUpdatedEvent.getUserEmail();
            message.setTo(userEmail);
            message.setSubject("Order Update - #" + orderUpdatedEvent.getOrderId());

            StringBuilder content = new StringBuilder();

            String userName = orderUpdatedEvent.getUserName();
            if (userName != null && !userName.isEmpty()) {

                String formattedName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
                content.append("Hi ").append(formattedName).append(",\n\n");
            } else {
                content.append("Hi,\n\n");
            }

            content.append("Your order ID : ").append(orderUpdatedEvent.getOrderId()).append(" has been updated.\n\n");

            content.append("Ordered Products:\n");
            List<String> productNames = orderUpdatedEvent.getProductNames();
            if (productNames != null && !productNames.isEmpty()) {
                int count = 1;
                for (String productName : productNames) {
                    content.append(count++).append(". ").append(productName).append("\n");
                }
            } else {
                content.append("No products available.\n");
            }

            content.append("\nCurrent Status of your Order : ").append(orderUpdatedEvent.getOrderStatus()).append("\n");

            content.append("\nThank you for shopping with us!");

            message.setText(content.toString());
            mailSender.send(message);
        };
    }
}
