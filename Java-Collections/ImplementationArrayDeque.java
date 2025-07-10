import java.util.ArrayDeque;

public class ImplementationArrayDeque {
    public static void main(String[] args) {
        ArrayDeque<Integer> arr = new ArrayDeque<>();

        arr.add(202);
        arr.add(307);
        arr.add(101);
        arr.add(560);
        arr.add(340);

        System.out.println(arr);

        System.out.println(arr.offerFirst(700));

        System.out.println(arr);

        System.out.println(arr.getLast());

        System.out.println(arr);

        System.out.println(arr.peek());

        System.out.println(arr);

        System.out.println(arr.pollFirst());

        System.out.println(arr);

        System.out.println(arr.pollLast());

        System.out.println(arr);

        System.out.println(arr.pop());

        System.out.println(arr);
    }
}
