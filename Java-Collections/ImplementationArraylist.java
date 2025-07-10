import java.util.ArrayList;
import java.util.List;

public class ImplementationArraylist {
    public static void main(String[] args) {
        List<ArrayListAdmins> arrayListAdmins = new ArrayList<>();

        arrayListAdmins.add(new ArrayListAdmins(1,"Surya", 22,"Java"));
        arrayListAdmins.add(new ArrayListAdmins(2,"Suchind",23,"Python"));
        arrayListAdmins.add(new ArrayListAdmins(3,"Senthil",23, "MERN"));


        arrayListAdmins.set(1,new ArrayListAdmins(2,"Sujeeth",23,"AI/ML"));

        arrayListAdmins.remove(2);
        arrayListAdmins.forEach(System.out::println);

        ArrayListAdmins check = new ArrayListAdmins(2,"Sujeeth", 23,"AI/ML");
        System.out.println(arrayListAdmins.contains(check));

        System.out.println(arrayListAdmins.size());

        arrayListAdmins.clear();

        arrayListAdmins.add(0,check);

        System.out.println(arrayListAdmins.isEmpty());

    }
}
