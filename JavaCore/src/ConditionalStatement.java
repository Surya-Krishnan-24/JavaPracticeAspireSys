import java.util.Scanner;

public class ConditionalStatement {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.print("No of products in cart: ");
        int product_count = scan.nextInt();

        if(product_count < 4 && product_count>0){
            System.out.println("add more products to avail discount");
        }
        else if(product_count >= 4){
            System.out.println("You can avail 10% discount now");
        }
        else{
            System.out.println("Add more than 4 product to avail offer");
        }

        System.out.println("Enter a number between 1 to 4");
        int selection = scan.nextInt();
        switch (selection){
            case 1:
                System.out.println("odd number");
                break;
            case 2:
                System.out.println("even number");
                break;
            case 3:
                System.out.println("odd number ");
                break;
            case 4:
                System.out.println("even number ");
                break;
            default:
                System.out.println("enter a valid number");
                break;
        }
    }
}
