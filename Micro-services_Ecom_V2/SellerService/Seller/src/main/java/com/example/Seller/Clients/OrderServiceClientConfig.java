package com.example.Seller.Clients;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;



@Configuration
public class OrderServiceClientConfig {


    @Bean
    public OrderServiceClient orderServiceClient(RestClient.Builder restClientBuilder){
        RestClient restClient = restClientBuilder
                .baseUrl("http://order-service")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (((request, response) -> {

                })))
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(OrderServiceClient.class);
    }

}
