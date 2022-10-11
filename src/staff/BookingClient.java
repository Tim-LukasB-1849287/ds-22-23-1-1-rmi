package staff;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.Set;

import hotel.BookingDetail;

public class BookingClient extends AbstractScriptedSimpleTest {

	private Manager bmStub = null;

	public static void main(String[] args) throws Exception {
//		System.setProperty("java.rmi.server.hostname", "localhost");
		BookingClient client = new BookingClient();
		client.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/
	public BookingClient() {
		try {
			String host = null;
			//Look up the registered remote instance
//			bm = new BookingManager();
			Registry registry = LocateRegistry.getRegistry();
			bmStub = (Manager) registry.lookup("Manager");
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
		try {
			return bmStub.isRoomAvailable(roomNumber, date);

		} catch (RemoteException e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
		System.err.println("Is room available failed.");

		return false;
	}

	@Override
	public void addBooking(BookingDetail bookingDetail) throws Exception {
		try {
			bmStub.addBooking(bookingDetail);

		} catch (RemoteException e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public Set<Integer> getAvailableRooms(LocalDate date) {
		try {
			return bmStub.getAvailableRooms(date);

		} catch (RemoteException e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Set<Integer> getAllRooms() {
		try {
			return bmStub.getAllRooms();

		} catch (RemoteException e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
		return null;
	}
}
