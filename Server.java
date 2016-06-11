import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException, ParseException {
        try {
            Terminal terminal = null;

            JsonParser jsonParser = new JsonParser();
            ServerInformation serverInformation = jsonParser.readJSON();
            ServerSocket listener = new ServerSocket(serverInformation.getPort());
            Socket socket = listener.accept();

            ObjectInputStream serverInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream serverOutputStream = new ObjectOutputStream(socket.getOutputStream());

            terminal = (Terminal) serverInputStream.readObject();

            /*if(terminal.getTransactions().get(2).getAmount() <= 0){
                throw new NegativeAmountException();
            }*/
           /* for(int i=0;i<3;i++){
                if(terminal.getTransactions().get(0).getAmount() <= 0){
                    throw new NegativeAmountException();
                }
                if (terminal.getTransactions().get(i).getType()!= "deposit" ||
                    terminal.getTransactions().get(i).getType()!= "withdraw"){
                        throw new InvalidDepositTypeException();
                }
            }*/
            for (int i=0;i<terminal.getTransactions().size();i++){
                for (int j=0;j<serverInformation.getDeposits().size();j++){
                    if (terminal.getTransactions().get(i).getDeposit() == serverInformation.getDeposits().get(j).getId()){
                        serverInformation.getDeposits().get(j).setInitialBalance(terminal.getTransactions().get(i).getDeposit());
                        System.out.println("YOHO");
                    }
                }
            }



            ArrayList<Transaction> transactions = new ArrayList<Transaction>();
            transactions=terminal.getTransactions();
            System.out.println( "  " +transactions.get(1).getAmount());
            System.out.println( "  " +transactions.get(0).getAmount());
            System.out.println( "  " +transactions.get(2).getAmount());
            terminal.getTransactions().get(0).setAmount(4444);
            terminal.getTransactions().get(1).setAmount(5555);

            serverOutputStream.writeObject(terminal);

            serverInputStream.close();
            serverOutputStream.close();

            socket.close();
            listener.close();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }/* catch (NegativeAmountException e) {
            System.out.println("Invalid Amount");
        }*/ /*catch (InvalidDepositTypeException e) {
            System.out.println("Invalid deposit type");
        }*/
    }

    }
