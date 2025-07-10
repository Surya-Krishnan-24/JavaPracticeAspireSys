import java.util.HashMap;
import java.util.Map;

public class ImplementationHashMap {
    public static void main(String[] args) {

        Map<String, HashMapLogin> login = new HashMap<>();
        login.put("surya", new HashMapLogin("surya","surya123"));
        login.put("senthil", new HashMapLogin("senthil","senthil123"));
        login.put("suchind", new HashMapLogin("suchind","suchind123"));

        System.out.println(login.get("surya"));

        System.out.println(login.containsKey("senthil"));

        System.out.println(login.containsValue(new HashMapLogin("surya","surya123")));

        for(String i : login.keySet()){
            System.out.println(i);
        }

        for(HashMapLogin i : login.values()){
            System.out.println(i);
        }

        login.clear();

        System.out.println(login.isEmpty());
    }

}
