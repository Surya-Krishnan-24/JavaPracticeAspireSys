//Separate odd numbers and even numbers from the below list
//Integer list = {2,4,6,7,9,0,1,13,16,23}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class practice2 {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(2,4,6,7,9,0,1,13,16,23));

        System.out.println("Even Number: ");
        list.stream().filter(num -> num%2 == 0).forEach(System.out::println);
        System.out.println("Odd Number: ");
        list.stream().filter(num -> num%2!=0).forEach(System.out::println);
    }
}


