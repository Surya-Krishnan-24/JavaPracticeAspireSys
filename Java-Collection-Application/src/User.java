import java.util.*;

public class User {

    Scanner scan = new Scanner(System.in);
    Map<String, String> users = new HashMap<>();
    Hotel hotel = new Hotel();


    public void userRegister() {
        System.out.println();
        System.out.println("--- USER REGISTRATION ---");

        System.out.println("1.Login");
        System.out.println("2.Register");


        System.out.print("Select from the Above options: ");
        int selection = 0;
        try {
            selection = scan.nextInt();
        }
        catch (Exception e){
            System.out.println("Enter a valid Input.");

        }


        scan.nextLine();
        if( selection == 0) {
           userRegister();
        }

        else {
        switch (selection) {
            case 1:

                System.out.print("Enter Your Username: ");
                String username = scan.nextLine();

                System.out.print("Enter Your Password: ");
                String password = scan.nextLine();


                if (users.containsKey(username)) {
                    if (users.get(username).equals(password)) {

                        hotel.listHotel(username);
                        break;
                    } else {
                        System.out.println("Wrong Credentials... Retry with Correct Credentials");
                        userRegister();
                        break;
                    }
                } else {
                    System.out.println("Wrong Credentials... Retry with Correct Credentials");
                    userRegister();
                    break;
                }


            case 2:

                System.out.print("Enter a Username: ");
                String username1 = scan.nextLine();


                if (users.containsKey(username1)) {
                    System.out.println("UserName already exist... Try with a different Username");
                    userRegister();
                    break;

                } else {

                    System.out.print("Enter a password: ");
                    String password1 = scan.nextLine();


                    System.out.print("Retype the Password: ");
                    String confirmPassword = scan.nextLine();


                    if (password1.equals(confirmPassword)) {
                        users.put(username1, password1);
                        System.out.println();
                        System.out.println("Registration Successfully Completed");
                        System.out.println();
                    } else {
                        System.out.println("Password Mismatch... Retry once Again");
                    }

                    userRegister();
                    break;

                }
            default:
                System.out.println("Enter a valid input");
                userRegister();
                break;
        }

        }
    }

}
