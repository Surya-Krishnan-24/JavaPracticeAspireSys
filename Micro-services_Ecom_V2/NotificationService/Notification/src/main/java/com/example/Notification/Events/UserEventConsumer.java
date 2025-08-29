package com.example.Notification.Events;

import com.example.Notification.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UserEventConsumer {

    private final JavaMailSender mailSender;

    @Bean
    public Consumer<UserDTO> handleUserEvent() {
        return user -> {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            System.out.println(user.getEmail());
            message.setSubject("Welcome to Aspire E-Commerce Platform");

            StringBuilder content = new StringBuilder();
            content.append("Hi ")
                    .append(capitalize(user.getFirstName()))
                    .append(" ")
                    .append(capitalize(user.getLastName()))
                    .append(",\n\n");

            content.append("Thanks for creating a ").append(user.getRole()).append(" account with Aspire Ecom\n\n");
            content.append("Here are your details:\n");
            content.append("Username: ").append(user.getUsername()).append("\n");
            content.append("Email: ").append(user.getEmail()).append("\n");
            content.append("Mobile: ").append(user.getMobileNo()).append("\n\n");

            if (user.getUserAddress() != null) {
                content.append("Your Address:\n");
                content.append(user.getUserAddress().getHouseNo()).append(", ");
                content.append(user.getUserAddress().getStreet()).append(",\n");
                content.append(user.getUserAddress().getCity()).append(", ");
                content.append(user.getUserAddress().getState()).append(",\n");
                content.append(user.getUserAddress().getCountry()).append(" - ");
                content.append(user.getUserAddress().getPincode()).append("\n\n");
            }

            content.append("If you have any questions, feel free to reach out to us.\n\n");

            message.setText(content.toString());
            mailSender.send(message);
        };
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return "";
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
