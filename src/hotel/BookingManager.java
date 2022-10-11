package hotel;

import staff.Manager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.*;

public class BookingManager implements Manager {

	private Room[] rooms;

	public BookingManager() {
		this.rooms = initializeRooms();
	}

	public Set<Integer> getAllRooms() {
		Set<Integer> allRooms = new HashSet<Integer>();
		Iterable<Room> roomIterator = Arrays.asList(rooms);
		for (Room room : roomIterator) {
			allRooms.add(room.getRoomNumber());
		}
		return allRooms;
	}

	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
		//implement this method
		Iterable<Room> roomIterator = Arrays.asList(rooms);
		for (Room room : roomIterator) {
			if (room.getRoomNumber().equals(roomNumber)){
				return room.isAvailable(date);
			}
		}
		// Maybe throw exception room non-existent
		return false;
	}


	public void addBooking(BookingDetail bookingDetail) {
		Integer roomNum = bookingDetail.getRoomNumber();
		Iterable<Room> roomIterator = Arrays.asList(rooms);
		for (Room room : roomIterator) {
			if (room.getRoomNumber().equals(roomNum)){
				List<BookingDetail> bookings = room.getBookings();
				bookings.add(bookingDetail);
				room.setBookings(bookings);
			}
		}
	}

	public Set<Integer> getAvailableRooms(LocalDate date) {
		Set<Integer> availableRooms =  new HashSet<Integer>();
		//implement this method
		Iterable<Room> roomIterator = Arrays.asList(rooms);
		for (Room room : roomIterator) {
			if (room.isAvailable(date)){
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
		try {
			System.setSecurityManager(null);
//System.setProperty("java.rmi.server.hostname","localhost");
//			LocateRegistry.createRegistry(8080);
			BookingManager obj = new BookingManager();
			Manager stub = (Manager) UnicastRemoteObject.exportObject(obj, 0);

			//Bind remote stub in registry
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Manager", stub);

			System.err.println("Manager server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
