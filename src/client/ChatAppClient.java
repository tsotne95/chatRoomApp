package client;

import interfaces.ChatApp;
import interfaces.Receivable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * @author Tsotne
 */
public class ChatAppClient extends UnicastRemoteObject implements Receivable {

    private ChatApp chatServer;
    private String userName;

    protected ChatAppClient(ChatApp chatServer, String userName) throws RemoteException {
        super();
        this.chatServer = chatServer;
        this.userName = userName;
    }

    @Override
    public void ReceiveNewMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public String getName() throws RemoteException {
        return this.userName;
    }

    private void runClient() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        String command;
        while(true){
            command = scan.nextLine();
            if(command.equals("/help"))
                System.out.println(chatServer.getHelp());
            else if(command.equals("/who"))
                System.out.println(chatServer.getClients());
            else if(command.equals("/quit"))
                break;
            else if(command.startsWith("/nick")){
                try{
                    String newUsername = command.split("\\s+")[1];
                    if(chatServer.setNickname(newUsername)){
                        chatServer.sendMessages("Changed name to "+ newUsername, this.userName);
                        this.userName =newUsername;
                    }else{
                        System.out.println("Name is already taken, please try another name");
                    }
                } catch ( ArrayIndexOutOfBoundsException a) {
                    System.out.println("You have to define new nickname");
                }
            }else{
                chatServer.sendMessages(command, this.userName);
            }
        }
        scan.close();
        System.out.println("Exiting...");
        chatServer.deRegisterChat(this);
        System.exit(0);
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("usage: java AuctionClient <server_host> <username>");
            System.exit(0);
        }

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 2222);
            ChatApp chat = (ChatApp) registry.lookup("chatappserver");
            ChatAppClient client = new ChatAppClient(chat,args[1]);


            if(!chat.registerForChat(client))
                System.out.println("Username was taken, try with something else!");
            else
            {
                String hist=chat.getHistory();
                System.out.print(hist);
                client.runClient();
            }

        }
        catch (NotBoundException nbe) {
            System.out.println(nbe.toString());
            System.out.println(args[0] + " is not available");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
