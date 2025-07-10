

import java.util.Scanner;

public class First {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int[][] arr = new int[2][3];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = scan.nextInt();
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        int f = (int) (Math.random() * 10);
        System.out.println(f);

        Calculator calc = new Calculator();
        System.out.print("Enter value of a: ");
        int a = scan.nextInt();
        System.out.print("Enter value of b: ");
        int b = scan.nextInt();
        calc.show(a, b);
        calc.show("surya", 2);
        int[][][] arr2 = new int[10][10][10];

        for (int i = 0; i < arr2.length; i++) {
            for (int j = 0; j < arr2[i].length; j++) {
                for (int k = 0; k < arr2[j].length; k++) {
                    arr2[i][j][k] = (int) (Math.random() * 10);
                }
            }

        }

        for (int[][] a1 : arr2) {
            for (int[] b1 : a1) {
                for (int c : b1) {
                    System.out.print(c);
                }
                System.out.println();
            }
            System.out.println();
        }
        StringBuffer sb = new StringBuffer();
        sb.ensureCapacity(17);
        sb.append("123456789012345678");
        System.out.println(sb);

        new Calculator();
        Calculator.show();

        Calculator.AdvCalc ac = calc.new AdvCalc();
        int add = ac.add(5, 6);
        int sub = ac.sub(5, 2);
        System.out.println(add);
        System.out.println(sub);
        Calculator obj = new Calculator();
        Calculator.B obj1 = obj.new B();
        obj1.show();


        Calculator obj4 = new Calculator();
    }

    ;




    }


