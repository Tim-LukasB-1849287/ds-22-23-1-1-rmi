package hotel;

import staff.Manager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingManager implements Manager {

    private Room[] rooms;
    private static final String _ServerStubName = "Manager";
    private static final Logger logger = Logger.getLogger(BookingManager.class.getName());

    public BookingManager() {
        this.rooms = initializeRooms();
    }

    public Set<Integer> getAllRooms() {
        logger.log(Level.INFO, "Getting all rooms");

        Set<Integer> allRooms = new HashSet<Integer>();
        Iterable<Room> roomIterator = Arrays.asList(rooms);
        for (Room room : roomIterator) {
            allRooms.add(room.getRoomNumber());
        }
        return allRooms;
    }

    public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
        logger.log(Level.INFO, "Checking availability for room with roomnumber {0} and for the date {1}", new Object[]{roomNumber, date});
        Iterable<Room> roomIterator = Arrays.asList(rooms);
        for (Room room : roomIterator) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room.isAvailable(date);
            }
        }
        // Maybe throw exception room non-existent
        return false;
    }


    public void addBooking(BookingDetail bookingDetail) {
        Integer roomNum = bookingDetail.getRoomNumber();
        logger.log(Level.INFO, "Adding a booking for room with number {0}", new Object[]{roomNum});
        Iterable<Room> roomIterator = Arrays.asList(rooms);
        for (Room room : roomIterator) {
            if (room.getRoomNumber().equals(roomNum)) {
                List<BookingDetail> bookings = room.getBookings();
                bookings.add(bookingDetail);
                room.setBookings(bookings);
            }
        }
    }

    public Set<Integer> getAvailableRooms(LocalDate date) {
        logger.log(Level.INFO, "Getting available rooms for date {0}", new Object[]{date});

        Set<Integer> availableRooms = new HashSet<>();
        Iterable<Room> roomIterator = Arrays.asList(rooms);
        for (Room room : roomIterator) {
            if (room.isAvailable(date)) {
                availableRooms.add(room.getRoomNumber());
            }
        }
        return availableRooms;
    }

    private static Room[] initializeRooms() {
        Room[] rooms = new Room[4];
        rooms[0] = new Room(101);
        rooms[1] = new Room(102);
        rooms[2] = new Room(201);
        rooms[3] = new Room(203);
        return rooms;
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() != null)
            System.setSecurityManager(null);
        try {
//          System.setProperty("java.rmi.server.hostname","localhost");
//			LocateRegistry.createRegistry(8080);
            Manager obj = new BookingManager();

            //Bind remote stub in registry
            Registry registry = null;
            try {
                registry = LocateRegistry.getRegistry();

            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "Could not locate RMI registry.");
                System.exit(-1);
            }

            Manager stub = (Manager) UnicastRemoteObject.exportObject(obj, 0);
            try {
                registry.rebind(_ServerStubName, stub);
                logger.log(Level.INFO, "Booking manager is registered.");

            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "Could not get stub bound of Booking manager.");
                e.printStackTrace();
                System.exit(-1);
            }

            System.err.println("Manager server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
