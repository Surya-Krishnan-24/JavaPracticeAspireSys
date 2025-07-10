

interface K {
    void show();

}

interface H extends K {

}

public class LambdaExpression {
    public static void main(String[] args) {
        H obj = () -> {
               System.out.println("In A Show");

        };


        obj.show();
    }
}


