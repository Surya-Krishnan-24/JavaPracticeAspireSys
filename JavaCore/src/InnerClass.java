class Mobile4{
    int price = 30_000;
    void show(){
        System.out.println("it is Mobile class");
    }
    class Samsung{
        void show(){
            System.out.println("it is a samsung class "+ price);
        }
    }
}

public class InnerClass {
    public static void main(String[] args) {
        Mobile4 mobile = new Mobile4();
        Mobile4.Samsung samsung = mobile.new Samsung();
        samsung.show();
    }
}
