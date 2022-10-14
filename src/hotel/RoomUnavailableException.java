package hotel;

import java.rmi.RemoteException;

public class RoomUnavailableException extends RemoteException {
    RoomUnavailableException(String string) {
        super(string);
    }

}
