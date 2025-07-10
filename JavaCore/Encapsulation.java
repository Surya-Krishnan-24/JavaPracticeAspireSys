class Setting{
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}


public class Encapsulation {
    public static void main(String[] args) {


        Setting setting = new Setting();
        setting.setName("Surya");
        setting.setAge(23);

        System.out.println(setting.getName());
        System.out.println(setting.getAge());
    }
}
