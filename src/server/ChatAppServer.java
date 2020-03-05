package server;

import interfaces.ChatApp;
import interfaces.Receivable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Tsotne
 */
public class ChatAppServer extends UnicastRemoteObject implements ChatApp {
    private ArrayList<Receivable> clientList = null;
    private ArrayList<String> history=null;
    private String backupFile="backup.txt";

    protected ChatAppServer() throws RemoteException {
        super();
        clientList = new ArrayList<Receivable>();
        history = new ArrayList<String>();
        File backup = new File(backupFile);
        try {
            backup.createNewFile();
            try {
                history = (ArrayList<String>) Files.readAllLines(Paths.get(backupFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
    }

    @Override
    public String getClients() throws RemoteException {
        StringBuilder usersList = new StringBuilder();
        usersList.append("Active users:\n");
        Iterator<Receivable> iter = clientList.iterator();
        while (iter.hasNext()) {
            try{
                usersList.append(iter.next().getName()+"\n");
            }catch(RemoteException re){
                iter.remove();
            }
        }
        usersList.deleteCharAt(usersList.length()-1);
        return usersList.toString();
    }

    @Override
    public String getHelp() throws RemoteException {
        return "Help\n/quit -- to quit \n/who -- to see active users \n/nick <new name> -- change nickname\n";
    }

    @Override
    public boolean setNickname(String name) throws RemoteException {
        Iterator<Receivable> iter = clientList.iterator();
        while (iter.hasNext()) {
            try{
                if(iter.next().getName().equals(name)){
                    return false;
                }
            }catch(RemoteException re){
                iter.remove();
            }
        }
        return true;
    }

    @Override
    public void sendMessages(String message, String user) throws RemoteException {
        Iterator<Receivable> iter = clientList.iterator();

        history.add(user+"---> "+message);
        try {
            FileWriter myWriter = new FileWriter(backupFile,true);
            myWriter.write(user+"---> "+message+"\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        while (iter.hasNext()) {
            try{
                iter.next().ReceiveNewMessage(user+"---> "+message);
            }catch(RemoteException re){
                iter.remove();
            }
        }
    }

    @Override
    public String getHistory() throws RemoteException {
        StringBuilder hist = new StringBuilder();
        Iterator<String> iter = history.iterator();
        while (iter.hasNext()) {
            try{
                hist.append(iter.next()+"\n");
            }catch(Exception re){
                iter.remove();
            }
        }
        return hist.toString();
    }

    @Override
    public boolean registerForChat(Receivable n) throws RemoteException {
        if(this.setNickname(n.getName())){
            clientList.add(n);
            return true;
        }
        return false;
    }

    @Override
    public void deRegisterChat(Receivable n) throws RemoteException {
        clientList.remove(n);
    }

    public static void main(String[] args) {
        try {
            ChatAppServer cas = new ChatAppServer();
            Registry registry = LocateRegistry.createRegistry(2222);

            registry.rebind("chatappserver", cas);
            System.out.println("ChatAppServer is running...");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
