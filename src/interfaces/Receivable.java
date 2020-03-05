package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Tsotne
 */
public interface Receivable extends Remote {
    public void ReceiveNewMessage(String message) throws RemoteException;
    public String getName() throws RemoteException;
}
