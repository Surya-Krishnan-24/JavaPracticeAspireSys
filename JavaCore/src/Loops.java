import java.lang.reflect.Array;

public class Loops {
    public static void main(String[] args) {

        int[] arr = {5,4,6,7,8};

        for(int i=0;i<5;i++){
            System.out.println(arr[i]);
        }

        int i =0;
        do{
            System.out.println(i);
        }
        while (i>5);


        while(i>5){
            System.out.println(i);
        }
    }
}
