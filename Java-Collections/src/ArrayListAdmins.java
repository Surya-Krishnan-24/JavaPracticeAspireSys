import java.util.Objects;

public class ArrayListAdmins {
    int ID;
    String name;
    int age;
    String Department;

    public ArrayListAdmins(int ID, String name, int age, String department) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        Department = department;
    }

    @Override
    public String toString() {
        return "ArrayListAdmins{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", Department='" + Department + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ArrayListAdmins that = (ArrayListAdmins) o;
        return ID == that.ID && age == that.age && Objects.equals(name, that.name) && Objects.equals(Department, that.Department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, age, Department);
    }
}

