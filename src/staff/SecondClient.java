package staff;

import hotel.BookingDetail;

import java.time.LocalDate;

public class SecondClient extends BookingClient {

    public static void main(String[] args) throws Exception {
//		System.setProperty("java.rmi.server.hostname", "localhost");
        SecondClient client = new SecondClient();
        client.run2();
    }

    public SecondClient(){
        super();
    }

    private final LocalDate day = LocalDate.of(2022,10,25);

    public void run2() throws Exception {

        //Print all rooms of the hotel
        printAllRooms();

        isRoomAvailable(101, day); //true
        BookingDetail bd1 = new BookingDetail("Tom", 101, day);
        addBooking(bd1);//booking success
        System.out.println("|-- Second client just tried to make a booking for room number 101.\n");

        //Check available rooms after the first booking

        isRoomAvailable(102, day); //true
        BookingDetail bd2 = new BookingDetail("Tim", 102, day);
        addBooking(bd2);//booking success
        System.out.println("|-- Second client just tried to make a booking for room number 102.\n");

        //Check available rooms after the second booking

        isRoomAvailable(203, day); //false
        BookingDetail bd3 = new BookingDetail("Toon", 203, day);
        addBooking(bd3);//booking failure
        System.out.println("|-- Second client just tried to make a booking for room number 203.\n");

        //Check available rooms after the booking failure
    }


    private void printAllRooms() throws Exception {
        System.out.println("List of rooms (room ID) in the hotel " + getAllRooms() + "\n");
    }


}

