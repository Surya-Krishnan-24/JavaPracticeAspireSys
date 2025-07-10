class Show{
        int age;
        void show(){
            System.out.println("show is printing");
        }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}



public class AnnonymousObject {
    public static void main(String[] args) {
        new Show().show();

        new Show().setAge(22);

        System.out.println(new Show().age);


    }
}
