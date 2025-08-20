package com.example.OrderService.Clients;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;



@Configuration
public class UserServiceClientConfig {


    @Bean
    public UserServiceClient userServiceClient(RestClient.Builder restClientBuilder){
        RestClient restClient = restClientBuilder
                .baseUrl("http://USER-SERVICE")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> {
                    System.err.println("User service returned 4xx error: " + response.getStatusCode());
                })
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        UserServiceClient userServiceClient = factory.createClient(UserServiceClient.class);
        return userServiceClient;
    }


}
