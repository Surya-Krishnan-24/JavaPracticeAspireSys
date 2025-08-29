package com.example.Notification.Events;

import com.example.Notification.DTO.ProductAddedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ProductEventConsumer {

    private final JavaMailSender mailSender;

    @Bean
    public Consumer<ProductAddedEvent> handleProductEvent() {
        return productAddedEvent -> {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(productAddedEvent.getSellerEmail());
            message.setSubject("New Product Created: " + productAddedEvent.getName());

            StringBuilder content = new StringBuilder();
            content.append("A new product has been created with the following details:\n\n");
            content.append("Product ID: ").append(productAddedEvent.getId()).append("\n");
            content.append("Name: ").append(productAddedEvent.getName()).append("\n");
            content.append("Description: ").append(productAddedEvent.getDescription()).append("\n");
            content.append("Price: ₹").append(productAddedEvent.getPrice()).append("\n");
            content.append("Stock Quantity: ").append(productAddedEvent.getStockQuantity()).append("\n");
            content.append("Category: ").append(productAddedEvent.getCategory()).append("\n");
            content.append("Seller: ").append(productAddedEvent.getSeller()).append("\n");
            content.append("Active: ").append(productAddedEvent.getActive() ? "Yes" : "No").append("\n");

            message.setText(content.toString());

            mailSender.send(message);
        };
    }
}
