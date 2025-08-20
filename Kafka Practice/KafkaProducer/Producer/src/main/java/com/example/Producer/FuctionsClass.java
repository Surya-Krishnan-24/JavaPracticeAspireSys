package com.example.Producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class FuctionsClass {

    @Bean
    public Function<String, String> uppercase(){
        return r -> r.toUpperCase();
    }
}
