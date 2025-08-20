package com.example.Producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.function.Supplier;

@Configuration
public class KafkaProducer {

    @Bean
    public Supplier<RiderLocation> sendRiderLocation(){
        Random random = new Random();
        return() -> {
            String riderId = "rider"+ random.nextInt(20);
            RiderLocation location = new RiderLocation(riderId,65.98938383,94.83736363);
            System.out.println("Sending: "+ location.getRiderId());
            return location;
        };
    }


}
