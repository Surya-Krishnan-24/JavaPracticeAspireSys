package org.example.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("org.example")
public class AppConfig {

//
//    @Bean
//    public Employee employee(@Autowired Computer com){
//        Employee obj = new Employee();
//        obj.setAge(22);
//        obj.setLap(com);
//        return obj;
//    }
//
//
//    @Bean(name = "laptop")
//    @Scope(value = "prototype")
//    public Laptop lap(){
//        return new Laptop();
//    }


}
