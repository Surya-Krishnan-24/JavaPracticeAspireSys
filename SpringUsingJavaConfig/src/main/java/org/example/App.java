package org.example;

import org.example.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


        Employee employee = context.getBean(Employee.class);
        System.out.println(employee.getAge());
        employee.code();




        Laptop laptop = context.getBean("laptop",Laptop.class);
        laptop.setYear(2022);
        laptop.compile();

        Laptop laptop1 = context.getBean("laptop",Laptop.class);
        laptop1.setYear(2030);
        System.out.println(laptop.getYear());
        System.out.println(laptop1.getYear());
        laptop1.compile();




















//        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
//        Employee employee = context.getBean("Employee", Employee.class);
//        employee.code();
//        System.out.println(employee.getAge());
//        employee.code();



    }
}
