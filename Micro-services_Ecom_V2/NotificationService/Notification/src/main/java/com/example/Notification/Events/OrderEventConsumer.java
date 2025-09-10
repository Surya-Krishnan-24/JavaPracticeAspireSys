package com.example.Notification.Events;

import com.example.Notification.DTO.NotificationOrderResponse;
import com.example.Notification.DTO.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final JavaMailSender mailSender;

    @Bean
    public Consumer<OrderCreatedEvent> handleOrderEvent(){

        return orderCreatedEvent -> {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(orderCreatedEvent.getUserEmail());
            message.setSubject("Order Confirm - #" + orderCreatedEvent.getOrderId());

            StringBuilder content = new StringBuilder();
            content.append("Hi ").append(orderCreatedEvent.getUserName().substring(0, 1).toUpperCase()).append(orderCreatedEvent.getUserName().substring(1)).append(",\n");
            content.append("Thanks for your order...\n\n Your items are\n");

            int j =1;
            int quantity = 0;
            for(NotificationOrderResponse i : orderCreatedEvent.getNotificationOrderResponses()){
                content.append("\n");
                content.append(j).append(". ").append(i.getName()).append("\n");
                content.append("Price of the Product : ").append(i.getPrice()).append("\n");
                content.append("Quantity : ").append(i.getQuantity()).append("\n");
                quantity = quantity + i.getQuantity();
                content.append("Total : ").append(i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity()))).append("\n");
                j++;
            }
            content.append("\n");
            content.append("\n");
            content.append("Total Number of Items: ").append(quantity).append("\n");
            content.append("Total Order Amount is  ₹: ").append(orderCreatedEvent.getTotalAmount()).append("\n");
            content.append("Status of your Order is : ").append(orderCreatedEvent.getStatus()).append("\n");
            message.setText(content.toString());
            mailSender.send(message);
        };
    }
}
