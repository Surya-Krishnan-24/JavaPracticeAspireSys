package com.example.Consumer;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class KafkaConsumer {

    @Bean
    public Consumer<RiderLocation> processRiderLocation(){
        return location -> {
            System.out.println("Recieved Location: "+ location.getRiderId()
            +" @"+ location.getLatitude()+ " , "+ location.getLongitude());
        };
    }

}
