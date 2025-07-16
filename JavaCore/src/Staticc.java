class Mobile{
    final static String brand = "Samsung";
    String name;

    static {
        System.out.println("static block called");
    }
    public static void show(){
        System.out.println("This is from "+ brand +" Store");
    }
}




public class Staticc {
    public static void main(String[] args) throws ClassNotFoundException {

            Class.forName("Mobile");


            Mobile mobile = new Mobile();
            mobile.name = "Samsung Galaxy S22";

            Mobile mobile1 = new Mobile();
            mobile1.name = "Samsung Galaxy S23";


            System.out.println(mobile1.name);
        System.out.println(mobile.name);
            System.out.println(Mobile.brand);

            Mobile.show();
    }
}
