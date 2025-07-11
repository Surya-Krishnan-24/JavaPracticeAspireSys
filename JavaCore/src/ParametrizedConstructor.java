class Values{

    private String name;
    private int age;
    public Values(){

    }
    public Values(String name, int age){
        this.name = name;
        this.age = age;
    }

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


public class ParametrizedConstructor {
    public static void main(String[] args) {
        Values values = new Values("Surya", 22);
        System.out.println(values.getName());
        System.out.println(values.getAge());
    }
}
