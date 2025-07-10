class Mobile1{
    final String brand = "SAMSUNG";
    String model;
    final void display(){
        System.out.println("Hello");
    }
}

class Mobile2 extends Mobile1{


    }




public class Final {
    public static void main(String[] args) {

        Mobile1 mobile1 = new Mobile1();
        mobile1.model = "Samsung galaxy s22";
        System.out.println(mobile1.brand);
        System.out.println(mobile1.model);

        Mobile1 mobile2 = new Mobile1();
        mobile2.model = "samsung galaxy s23";
        System.out.println(mobile2.model);
    }
}
