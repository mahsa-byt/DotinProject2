import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiThreadServer implements Runnable {
    private Socket socket;
    private final Logger serverLogger = Logger.getLogger(Server.class.getName());

    public MultiThreadServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        JsonParser jsonParser = new JsonParser();
        try {
            ServerInformation serverInformation = jsonParser.readJSON();
            FileHandler handler = new FileHandler(serverInformation.getOutLog(), true);
            serverLogger.addHandler(handler);

            serverLogger.log(Level.INFO, Thread.currentThread().getName() + " Parsed JSon file successfully.");
            //ObjectInputStream
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Transaction transaction;
                ArrayList<Deposit> depositArrayList = new ArrayList<Deposit>();
                String request;
//                do {
                serverLogger.log(Level.INFO, "request is coming from " + Thread.currentThread().getName() +".");

                request = (String) objectInputStream.readObject();

                serverLogger.log(Level.INFO, Thread.currentThread().getName() + " Received the request successfully");

                transaction = convertTransactionStrToTransactionObject(request);
                Deposit deposit = searchDeposit(transaction, serverInformation.getDeposits());

                if (deposit.getCustomer() != null) {
                    depositArrayList.add(deposit);
                    System.out.println(depositArrayList);
                    if (transaction.getAmount().compareTo(BigDecimal.ZERO) == 1) {
                        synchronized (deposit) {
                            transaction.validateRequest(deposit);
                            /*if (transaction.getTerminalId() == 21374) {
                                Thread.sleep(2000);
                            }*/
                            if (transaction.getRequestIsValid()) {
                                serverLogger.log(Level.INFO, "Request from "+Thread.currentThread().getName()+ " is valid");
                                transaction.applyChanges(deposit);
                                serverLogger.log(Level.INFO, "Changes were applied");
                            } else {
                                serverLogger.log(Level.INFO, "Request is not valid");
                            }
                        }
                        String response;
                        response = transaction.getId() + "," + transaction.getType() + "," + deposit.getInitialBalance() + "," + deposit.getId();
                        sendResponse(deposit.getId(), response);
                        serverLogger.log(Level.INFO, "server sent response");
                    } else if (transaction.getAmount().compareTo(BigDecimal.ZERO) == -1) {
                        sendResponse(transaction.getId(), "Fail: transaction amount is negative.");
                        serverLogger.log(Level.SEVERE, "Negative amount");
                    }
                } else {
                    sendResponse(transaction.getId(), "Fail: Invalid deposit ID.");
                    serverLogger.log(Level.SEVERE, "Invalid deposit type.");
                }
//                } while (request != null);
               System.out.println(depositArrayList);
//                serverSocket.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Deposit searchDeposit(Transaction transaction, ArrayList<Deposit> deposits) {
        Deposit resultOfSearch = new Deposit();
        for (Deposit deposit : deposits) {
            if ((transaction.getDeposit()) == deposit.getId()) {
                resultOfSearch = deposit;
            }
        }
        return resultOfSearch;
    }

    public void sendResponse(int depositID, String message) throws IOException {
        String response = depositID + "," + message;
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(response);
    }

    public Transaction convertTransactionStrToTransactionObject(String request) {
        String[] str = request.split(",");
        Integer transactionId = Integer.parseInt(str[0]);
        String transactionType = str[1];
        BigDecimal transactionAmount = new BigDecimal(str[2]);
        Integer transactionDeposit = Integer.parseInt(str[3]);
        return new Transaction(transactionId, transactionType, transactionAmount, transactionDeposit);
    }


}
