import java.util.TreeMap;

public class ImplementationTreeMap {

    public static void main(String[] args) {
        TreeMap<Integer,TreeMapProducts> products = new TreeMap<>();

        products.put(104,new TreeMapProducts(104,"boat Earbuds"));
        products.put(101,new TreeMapProducts(101,"sony charger"));
        products.put(104,new TreeMapProducts(104,"LG TV"));
        products.put(109,new TreeMapProducts(102,"Puma shoe"));


        System.out.println(products);

        System.out.println(products.firstKey());

        System.out.println(products.ceilingKey(105));

        System.out.println(products.floorKey(103));

        System.out.println(products.lastKey());

        for(int i: products.keySet()){
            System.out.println(products.get(i).productName);
        }

        System.out.println(products.values());

    }
}
