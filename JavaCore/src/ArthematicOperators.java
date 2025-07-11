public class ArthematicOperators {
    public static void main(String[] args) {


        int num1 = 50;
        int num2 = 26;

        int add = num1+num2;
        int sub = num1-num2;
        int multiply = num1*num2;
        int divide = num1/num2;
        int modulus = num1%num2;


        System.out.println(add);
        System.out.println(sub);
        System.out.println(multiply);
        System.out.println(divide);
        System.out.println(modulus);


        //Increment
        int a = 5;

        System.out.println("post "+ a++);       // post-increment

        System.out.println("pre "+ ++a);       // pre-increment

        //Decrement

        System.out.println("post " + a--);      // post-decrement

        System.out.println("pre " + --a);         // pre-decrement

    }
}
