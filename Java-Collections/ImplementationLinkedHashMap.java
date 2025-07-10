import java.util.LinkedHashMap;
import java.util.Map;

public class ImplementationLinkedHashMap {
    public static void main(String[] args) {
        Map<String,LinkedHashMapLogin> login = new LinkedHashMap<>();

        login.put("surya", new LinkedHashMapLogin("surya", "surya123"));
        login.put("suchind", new LinkedHashMapLogin("suchind", "suchind123"));
        login.put("senthil", new LinkedHashMapLogin("senthil", "senthil123"));

        System.out.println(login.get("surya"));

        System.out.println(login.isEmpty());

        System.out.println(login.containsKey("suchind"));

        System.out.println(login.containsValue("suchind"));

        System.out.println(login.keySet());
        login.clear();

        System.out.println(login.size());



    }
}
