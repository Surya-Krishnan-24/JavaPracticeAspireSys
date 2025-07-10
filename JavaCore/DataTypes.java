public class DataTypes {
    public static void main(String[] args) {


        //INTEGER
        byte a = 12;                    // 8 bits --  will start from -128 to 127
        short b = 32000;                // 16 bits -- will start from -32,768 to 32,767
        int c = 1_00_00_000;            // 32 bits --
        long d = 1_00_00_00_000L;       // 64 BITS


        // FLOAT
        float e = 32.34f;                 //32 bits
        double f = 77.9998878979;        //64 bits

        //Character
        char g = 'c';                   //16 bits

        //Boolean
        boolean h = true;               //1 bit


        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);
        System.out.println(f);
        System.out.println(g);
        System.out.println(h);



    }
}