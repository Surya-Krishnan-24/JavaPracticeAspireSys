interface A {
    void show();
}

interface C{
    void run();
}
class B implements A,C {

    public void show() {
        System.out.println("showing");
    }

    public void run() {
        System.out.println("running");
    }

}
//enum Status{
//    Running, Failed, Pending, Success;
//}

    enum Laptop {
        Lenovo(100), Acer(200), HP(300), MacBook(10000);

        private int price;

        private Laptop(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }


public class Demo{
    public static void main(String[] args) {
/*
        B obj = new B();
        obj.show();
        obj.run();
        Status s = Status.Failed;
        System.out.println(s);

        Status[] sarr = Status.values();
        for (Status i : sarr){
            System.out.println(i);
        }
        Laptop lp = Laptop.Acer;
        int price = lp.getPrice();
        System.out.println(price);
*/

        int a = 8;
        int b = 16;
        int c = 0;
        try{
           c = b/a;
           if(c == 2){
               throw new ArithmeticException("2 should not come");
           }
        }
        catch (ArithmeticException e){
            System.out.println("A must be greater than 0" + e);
        }
        System.out.println(c);

    }
}
