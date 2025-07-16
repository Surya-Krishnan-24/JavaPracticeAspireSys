package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Student s1 = new Student();

//        s1.setsName("Aarya");
//        s1.setsAge(23);
//        s1.setRollNo(40);

//        Student s2 = null;
//
//        SessionFactory sf = new Configuration()
//                .addAnnotatedClass(org.example.Student.class)
//                .configure()
//                .buildSessionFactory();
//
//        Session session = sf.openSession();
//
//        s2 = session.find(Student.class,40);
//
//        Transaction transaction = session.beginTransaction();
//        session.persist(s1);
//        transaction.commit();

//        s2 = session.find(Student.class, 40);

//        session.merge(s1);
//
//        session.remove(s2);
//        transaction.commit();
//        session.close();
//        sf.close();
//        System.out.println(s2);
//        System.out.println(s2);




        Laptop l1 = new Laptop();
        l1.setlID(1001);
        l1.setBrand("Asus");
        l1.setModel("ROG");
        l1.setRam(8);

        Laptop l2 = new Laptop();
        l2.setlID(1002);
        l2.setBrand("Lenovo");
        l2.setModel("strix");
        l2.setRam(16);

        Laptop l3 = new Laptop();
        l3.setlID(1003);
        l3.setBrand("Dell");
        l3.setModel("XPS");
        l3.setRam(8);

        Employee e1 = new Employee();
        e1.seteID(101);
        e1.seteName("Surya");
        e1.setTech("Java");
        e1.setLaptops(List.of(l1,l2));

        Employee e2 = new Employee();
        e2.seteID(102);
        e2.seteName("Senthil");
        e2.setTech("MERN");
        e2.setLaptops(List.of(l1,l2));

        Employee e3 = new Employee();
        e3.seteID(103);
        e3.seteName("Suchind");
        e3.setTech("Python");
        e3.setLaptops(List.of(l1,l2));


        e1.setLaptops(List.of(l1,l2));
        e2.setLaptops(List.of(l1,l3));
        e3.setLaptops(List.of(l3,l2));

        l1.setEmployees(List.of(e1,e2));
        l2.setEmployees(List.of(e1,e3));
        l3.setEmployees(List.of(e2,e3));




        SessionFactory sf = new Configuration()
                .addAnnotatedClass(org.example.Employee.class)
                .addAnnotatedClass(org.example.Laptop.class)
                .configure()
                .buildSessionFactory();


        Session session = sf.openSession();

        Transaction transaction = session.beginTransaction();

        session.persist(e1);
        session.persist(e2);
        session.persist(e3);
        session.persist(l1);
        session.persist(l2);
        session.persist(l3);

        transaction.commit();

        session.close();

        Session session1 = sf.openSession();

        Employee e4 = session1.find(Employee.class,103);
        System.out.println(e4);
        session1.close();





    }
}