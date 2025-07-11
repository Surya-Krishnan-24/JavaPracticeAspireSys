import java.util.LinkedList;
import java.util.List;

public class ImplementationLinkedList {
    public static void main(String[] args) {
        List<LinkedListProducts> products = new LinkedList<>();

        products.add(new LinkedListProducts(101,"Samsung s22 ultra", 80_000,"Mobile"));
        products.add(new LinkedListProducts(102,"Samsung s25", 90_000,"Mobile"));
        products.add(new LinkedListProducts(103,"Samsung Laptop", 1_00_000,"Laptop"));

        System.out.println(products);

        System.out.println(products.getFirst());
        System.out.println(products.getLast());

        System.out.println(products.size());

        products.remove(2);

        LinkedListProducts updateone = new LinkedListProducts(104,"Laptop Bag", 1200, "Bag");

        products.set(1,updateone);

        System.out.println(products);
    }
}
