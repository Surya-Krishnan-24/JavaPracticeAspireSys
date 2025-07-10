public class TypeConversion {
    public static void main(String[] args) {
        byte b = 127;
        int a =258;
        b = (byte)a;

        System.out.println(b);

        byte c = 127;
        int d =12;
        c = (byte)d;

        System.out.println(c);    //type casting



        byte num1 = 30;
        byte num2 = 10;
        int total = num1*num2;

        System.out.println(total);   //Type promotion
    }
}
