package hotel;

import java.rmi.RemoteException;

public class RoomNonExistentException extends RemoteException {
    RoomNonExistentException(String s) {
        super(s);
    }
}
