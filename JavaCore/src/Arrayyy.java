import java.util.Scanner;

public class Arrayyy {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int[] arr = {1, 2, 3, 4, 5, 6};

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+ " ");
        }
        System.out.println();
        
        for (int i: arr){
            System.out.print(i+ " ");
        }
        System.out.println();
        int[][] arr1 = new int[2][3];

        for (int i=0;i<arr1.length;i++){
            for (int j=0;j<arr1[i].length;j++){
                arr1[i][j] = scan.nextInt();
            }
        }

        for (int i=0;i<arr1.length;i++){
            for (int j=0;j<arr1[i].length;j++){
                System.out.print(arr1[i][j]+" ");;
            }
            System.out.println();
        }

        int[][] arr3 = new int[2][];
        arr3[0] = new int[2];
        arr3[1] = new int[3];


        for(int i=0;i<arr3.length;i++){
            for (int j=0;j<arr3[i].length;j++){
                arr3[i][j] = scan.nextInt();
            }
        }
        for (int[] n: arr3){
            for(int i: n){
                System.out.print(i);
            }
            System.out.println();
        }
    }

}
