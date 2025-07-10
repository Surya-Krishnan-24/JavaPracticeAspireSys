import java.util.Set;
import java.util.TreeSet;

public class ImplementationTreeset {
    public static void main(String[] args) {
        Set<TreesetProduct> products = new TreeSet<>();

        products.add(new TreesetProduct(101,"Firebolt watch",2_000,"Watch"));
        products.add(new TreesetProduct(102,"Mackbook Air",1_00_000,"Laptop"));
        products.add(new TreesetProduct(101,"Firebolt watch",2_000,"Watch"));
        products.add(new TreesetProduct(103,"Samsung s22",50_000,"Mobile"));

        System.out.println(products);

        products.remove(new TreesetProduct(103,"Samsung s22",50_000,"Mobile"));
        System.out.println(products);

        System.out.println(products.size());

        System.out.println(products.isEmpty());

        products.add(new TreesetProduct(103,"Samsung s22",50_000,"Mobile"));

        System.out.println(products.isEmpty());
    }
}
