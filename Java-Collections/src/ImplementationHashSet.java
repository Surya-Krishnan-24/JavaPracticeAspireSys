import java.util.HashSet;
import java.util.Set;

public class ImplementationHashSet {
    public static void main(String[] args) {
        Set<SetofUsername> username = new HashSet<>();

        username.add(new SetofUsername(101, "surya"));
        username.add(new SetofUsername(102, "aswanth"));
        username.add(new SetofUsername(101, "surya"));
        username.add(new SetofUsername(104, "vignesh"));

        System.out.println(username);

        System.out.println(username.isEmpty());

        System.out.println(username.size());

        SetofUsername check = new SetofUsername(101, "surya");

        System.out.println(username.contains(check));

        username.remove(check);

        System.out.println(username.isEmpty());



    }
}
