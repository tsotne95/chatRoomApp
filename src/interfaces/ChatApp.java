package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Tsotne
 */
public interface ChatApp extends Remote {
    public String getClients() throws RemoteException;
    public String getHelp() throws RemoteException;
    public boolean setNickname(String name) throws RemoteException;
    public void sendMessages(String message, String user) throws RemoteException;
    public String getHistory() throws RemoteException;
    //by clients
    public boolean registerForChat(Receivable n) throws RemoteException;
    public void deRegisterChat(Receivable n) throws RemoteException;
}
