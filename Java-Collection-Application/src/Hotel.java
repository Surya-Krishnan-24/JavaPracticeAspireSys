
public class Hotel {

    String hotelName;
    int roomsAvailable;
    int price;
    String category;

    public Hotel() {
    }

    public void setRoomsAvailable(int roomsAvailable) {
        this.roomsAvailable = roomsAvailable;
    }

    public Hotel(String hotelName, int roomsAvailable, int price, String category) {
        this.hotelName = hotelName;
        this.roomsAvailable = roomsAvailable;
        this.price = price;
        this.category = category;
    }

    HotelData hotelData = new HotelData();
    public void listHotel(String u){

        System.out.println();
        System.out.println("HOTEL BOOKING");
        System.out.println();
        hotelData.loadCategory();
        hotelData.selectCategory(u);



    }
}
