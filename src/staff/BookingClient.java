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
			Registry registry = LocateRegistry.getRegistry(host);
			bmStub = (Manager) registry.lookup("Manager");
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
		//Implement this method
		return true;
	}

	@Override
	public void addBooking(BookingDetail bookingDetail) throws Exception {
		//Implement this method
	}

	@Override
	public Set<Integer> getAvailableRooms(LocalDate date) {
		//Implement this method
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
