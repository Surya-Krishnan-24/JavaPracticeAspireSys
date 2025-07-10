class Methods {
        public void add(int a, int b){
            System.out.println(a+b);
    }
    public void add (int a, int b, int c){
        System.out.println(a+b+c);
    }
}

public class Overloading{
    public static void main(String[] args) {
        Methods me = new Methods();
        me.add(2,3);
        me.add(3,4,5);
    }
}
