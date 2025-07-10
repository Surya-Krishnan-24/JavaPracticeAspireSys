public  class Calculator {
    static {
        System.out.println("hello");


    }
    public static void show(){
        System.out.println("Hai");
    }
    int num2;

    public void show(int a, int b) {
        if (a > 100 && b > 100) {
            int result = a + b;
            System.out.println(result);
        }
        else{
            System.out.println("value is less than 100");
        }
    }
    public void show(String a, int b) {


            System.out.println(a);
        }



    class B {
        public void show() {
            System.out.println("B class");
        }
    }


   public class AdvCalc {
        public int add(int a, int b) {
            return a + b;
        }
        public int sub(int a,int b){
            return a - b;
        }

    }
}