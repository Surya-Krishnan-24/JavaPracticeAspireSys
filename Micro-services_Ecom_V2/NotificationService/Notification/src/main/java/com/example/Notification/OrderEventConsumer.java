package com.example.Notification;

import com.example.Notification.DTO.NotificationOrderResponse;
import com.example.Notification.DTO.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Consumer;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final JavaMailSender mailSender;

    @Bean
    public Consumer<OrderCreatedEvent> handleOrderEvent(){

        return orderCreatedEvent -> {
            log.info("Received Order created Event for order id : {}", orderCreatedEvent.getOrderId());
            log.info("Received Order created Event for user name : {}", orderCreatedEvent.getUserName());

            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(orderCreatedEvent.getUserEmail());
            message.setSubject("Order Confirm - " + orderCreatedEvent.getOrderId());

            StringBuilder content = new StringBuilder();
            content.append("Hi "+orderCreatedEvent.getUserName().substring(0,1).toUpperCase()+orderCreatedEvent.getUserName().substring(1)+",\n");
            content.append("Thanks for your order...\n\n Your items are\n");

            int j =1;
            for(NotificationOrderResponse i: orderCreatedEvent.getNotificationOrderResponses()){
                content.append("\n");
                content.append(j+". "+ i.getName()+"\n");
                content.append("Price of the Product : "+ i.getPrice()+"\n");
                content.append("Quantity : "+ i.getQuantity()+"\n");
                content.append("Total : "+ (i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity()))+"\n"));
                j++;
            }
            content.append("\n");
            content.append("\n");
            content.append("Total Number of Items: "+ orderCreatedEvent.getNotificationOrderResponses().size()+"\n");
            content.append("Total Order Amount is  ₹: " + orderCreatedEvent.getTotalAmount()+"\n");
            content.append("Status of your Order is : " + orderCreatedEvent.getStatus()+"\n");
            message.setText(content.toString());
            mailSender.send(message);

        };
    }
}
