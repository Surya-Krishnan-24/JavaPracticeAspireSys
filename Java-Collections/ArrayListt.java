import java.util.ArrayList;

import java.util.List;

public class ArrayListt {
    
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        list.add(5);
        list.add(10);
        list.add(15);
        list.add(2,20);

        System.out.println(list.get(2));

        list.set(1,7);        //update

        System.out.println(list.get(1));

        list.remove(1);

        System.out.println(list.contains(15));

        System.out.println(list.contains(7));

        System.out.println(list.indexOf(15));

        System.out.println(list.size());

        list.forEach(System.out::println);

        list.clear();

        System.out.println(list.isEmpty());




    }
}
