import java.util.Scanner;

public class LogicalOperators {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Mark: ");
        int mark = Integer.parseInt(scan.nextLine());

        if (mark>90 && mark <100){
            System.out.println("Excellent");
        }

        System.out.println("Enter climate now: ");
        String climate = scan.nextLine();

        if(climate.equals("Rainy") || climate.equals("Sunny")){
            System.out.println("Take an Umberlla");
        }

        System.out.println("Enter your age: ");

        int age = Integer.parseInt(scan.nextLine());
        if(age!=18){
            System.out.println("No voting right");
        }
    }
}
