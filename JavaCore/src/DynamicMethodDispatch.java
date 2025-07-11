class Car{
    void show(){
        System.out.println("showing");
    }
}

class BMW extends Car{
    void show(){
        System.out.println("BMW Showing");
    }
    void config(){
        System.out.println("Premium BMW");
    }
}




public class DynamicMethodDispatch {
    public static void main(String[] args) {
        Car car = new BMW();
        car.show();

        BMW bmw = new BMW();
        bmw.config();
    }
}
