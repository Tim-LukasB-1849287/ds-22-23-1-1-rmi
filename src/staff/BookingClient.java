package staff;

import hotel.BookingDetail;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.Set;

public class BookingClient extends AbstractScriptedSimpleTest {

    private Manager bmStub = null;
    private static final String _ManagerStubName = "Manager";

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
            //Look up the registered remote instance
//			bm = new BookingManager();
            Registry registry = LocateRegistry.getRegistry();
            bmStub = (Manager) registry.lookup(_ManagerStubName);
            if (bmStub == null) {
                System.err.println("Could not find manager with given name.");
                System.exit(-1);
            }
            System.out.println("Booking manager found.");

        } catch (NotBoundException e) {
            System.err.println("Could not find manager with given name.");
            e.printStackTrace();
            System.exit(-1);
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

    }

    @Override
    public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
        try {
            return bmStub.isRoomAvailable(roomNumber, date);

        } catch (RemoteException e) {
            System.err.println("Exception: " + e.toString());
        }
        System.err.println("Is room available failed.");

        return false;
    }

    @Override
    public void addBooking(BookingDetail bookingDetail) {
        try {
            bmStub.addBooking(bookingDetail);

        } catch (RemoteException e) {
            System.err.println("Exception: " + e.toString());
        }
    }

    @Override
    public Set<Integer> getAvailableRooms(LocalDate date) {
        try {
            return bmStub.getAvailableRooms(date);

        } catch (RemoteException e) {
            System.err.println("Exception: " + e.toString());
        }
        return null;
    }

    @Override
    public Set<Integer> getAllRooms() {
        try {
            return bmStub.getAllRooms();

        } catch (RemoteException e) {
            System.err.println("Exception: " + e.toString());
        }
        return null;
    }
}
