
import java.util.*;

class HotelData {


    Scanner scan = new Scanner(System.in);
    List<Hotel> hotels = new ArrayList<>();
    List<Booking> booking = new Stack<>();
    Set<Category> categories = new TreeSet<>();
    List<Hotel> filterarray = new ArrayList<>();


    public void loadCategory(){
        categories.add(new Category("1 Star"));
        categories.add(new Category("2 Star"));
        categories.add(new Category("3 Star"));
        categories.add(new Category("4 Star"));
        categories.add(new Category("5 Star"));
        categories.add(new Category("All"));


    }

    public void selectCategory(String u){

        System.out.println("---Choose the Category of hotel you need---");

        int j=1;
        for (Category cat: categories){
            System.out.println(j+". "+cat);
            j++;
        }

        System.out.print("Enter the category of hotel you need: ");

        int selection = 0;
        try {
            selection = scan.nextInt();
            scan.nextLine();

            if(selection>0 && selection<categories.size()+1){
                listAllHotels(selection, u);
            }
        }
        catch (Exception e){
            System.out.println();
            System.out.println("Enter a valid input");
            scan.nextLine();
            System.out.println();
            selectCategory(u);
        }
        System.out.println();



    }

    public void loadSampleHotel(){
        hotels.add(new Hotel("The Taj", 10, 4500, "5 Star"));
        hotels.add(new Hotel("Oberoi Grand", 5, 5200,"4 Star"));
        hotels.add(new Hotel("ITC Royal", 8, 4000,"5 Star"));
        hotels.add(new Hotel("Lemon Tree", 12, 3000,"3 Star"));
        hotels.add(new Hotel("Hyatt Regency", 6, 5500, "4 Star"));
        hotels.add(new Hotel("Radisson Blu", 9, 4700, "5 Star"));
        hotels.add(new Hotel("Budget Inn", 5, 1500, "1 Star"));
        hotels.add(new Hotel("City Lodge", 4, 1200, "1 Star"));
        hotels.add(new Hotel("Comfort Stay", 7, 2200, "2 Star"));
        hotels.add(new Hotel("Urban Nest", 6, 2000, "2 Star"));
        hotels.add(new Hotel("Metro Suites", 8, 3200, "3 Star"));
        hotels.add(new Hotel("Central Park Hotel", 7, 3100, "3 Star"));
    }




    public void listAllHotels(int cat, String username) {

        while (hotels.isEmpty()) {
            loadSampleHotel();
        }


        int i = 1;

        for (Hotel h : hotels) {
            if (cat == Integer.parseInt(String.valueOf(h.category.charAt(0)))) {
                if (h.roomsAvailable > 0) {
                    System.out.println();
                    System.out.println("Hotel ID: " + i);
                    System.out.print(i + ". ");
                    System.out.println("Hotel Name: " + h.hotelName);
                    System.out.println("No of Rooms Available: " + h.roomsAvailable);
                    System.out.println("Price per Room per night: " + h.price);
                    System.out.println("Category: " + h.category);
                    System.out.println();
                    filterarray.add(h);

                    i++;
                }
            } else if (cat == 6) {
                if (h.roomsAvailable > 0) {
                    System.out.println();
                    System.out.println("Hotel ID: " + i);
                    System.out.print(i + ". ");
                    System.out.println("Hotel Name: " + h.hotelName);
                    System.out.println("No of Rooms Available: " + h.roomsAvailable);
                    System.out.println("Price per Room per night: " + h.price);
                    System.out.println("Category: " + h.category);
                    System.out.println();
                    i++;
                    filterarray.add(h);
                }
            }


        }

        System.out.print("Enter the hotel ID to book: ");

        int selectHotel = scan.nextInt();


        Hotel selectHotelname = filterarray.get(selectHotel - 1);

        System.out.print("You have selected ");
        System.out.println("Hotel Name - " + selectHotelname.hotelName);
        System.out.println("No of Rooms Available - " + selectHotelname.roomsAvailable);
        System.out.println("Price of the Room - " + selectHotelname.price);

        System.out.println();

        System.out.print("Enter Number of rooms needed : ");
        scan.nextLine();


        int noOfRooms = scan.nextInt();

        if (selectHotelname.roomsAvailable < noOfRooms) {
            System.out.println();
            System.out.println("The " + selectHotelname.hotelName + " has only " + selectHotelname.roomsAvailable + " rooms but you booked "+noOfRooms);
            System.out.println("---To go back to Home press 1---");
            int home = scan.nextInt();
            if(home == 1) {
                listAllHotels(cat, username);
            }
            else{
                System.out.println("Enter a valid input");
            }
        }
        else {


            int total = selectHotelname.price * noOfRooms;

            int updatedRooms = selectHotelname.roomsAvailable - noOfRooms;


            booking.add(new Booking(username, selectHotelname.hotelName, noOfRooms, total));


            selectHotelname.setRoomsAvailable(updatedRooms);

            System.out.println();
            System.out.println("Successfully booked " + selectHotelname.hotelName + "\nTotal amount to Pay - ₹" + total);
            System.out.println();

            System.out.println("To see all your Booking History press 1");
            System.out.println("To go to Home page press 2");
            System.out.println();
            System.out.print("Enter number to Navigate: ");
            int nav = scan.nextInt();

            switch (nav) {
                case 1:
                    System.out.println();
                    System.out.println("Hotels Booked");
                    int j = 1;
                    for (Booking b : booking) {
                        System.out.println();
                        System.out.print(j + ". ");
                        System.out.println("Hotel Name - " + b.hotelName);
                        System.out.println("Booked By - " + b.username);
                        System.out.println("No of rooms booked - " + b.noOfRooms);
                        System.out.println("Total amount - " + b.total);
                        System.out.println();
                        j++;
                    }
                    System.out.println("To go to Home page press 1");

                    int nav1 = scan.nextInt();
                    if (nav1 == 1) {
                        selectCategory(username);
                    }
                    break;
                case 2:
                    selectCategory(username);
                    break;

            }
        }
    }



}
