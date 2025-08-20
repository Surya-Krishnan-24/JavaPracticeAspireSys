package com.example.Producer;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic createMyTopic(){
        return new NewTopic("my-new-topic-2",4,(short) 1);
    }
}
