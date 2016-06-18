import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RunServer{
    public static void main(String[] args){
        JsonParser jsonParser = new JsonParser();
        ServerInformation serverInformation = new ServerInformation();
        try {
            serverInformation=jsonParser.readJSON();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //final ServerInformation finalServerInformation = serverInformation;
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                List<Deposit> deposits = finalServerInformation.getDeposits();
                while (true){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (Deposit deposit : deposits){
                        System.out.println(deposit.getInitialBalance());
                    }
                }
            }
        }).start();*/
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(serverInformation.getPort());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
            while (true){
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                System.out.println("connection Established");
                new Thread(new MultiThreadServer(socket)).start();
            }
             catch (IOException e) {
            e.printStackTrace();
        }
    }


    }
}
