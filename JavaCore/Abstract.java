abstract class OnlineShopping{
    abstract void show();
}

class Flipkart extends OnlineShopping{

    @Override
    void show() {
        System.out.println("in online shopping");
    }
}

public class Abstract{
    public static void main(String[] args) {
        Flipkart flipkart = new Flipkart();
        flipkart.show();

    }
}
