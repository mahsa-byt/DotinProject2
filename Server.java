import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    private String threadName;
    ServerSocket serverSocket;
    Socket socket;
    private final Logger serverLogger = Logger.getLogger(Server.class.getName());

    public Server() {
    }

    public Server(String threadNameParameter) {
        threadName = threadNameParameter;
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

    public void sendResponse(int depositID, Transaction transactionResult) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(transactionResult);
    }

    public void sendResponse(int depositID, String message) throws IOException {
        String response = depositID + "," + message;
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(response);
    }

    @Override
    public void run() {
        JsonParser jsonParser = new JsonParser();
        try {
            FileHandler handler = new FileHandler("server.out", true);
            serverLogger.addHandler(handler);
            ServerInformation serverInformation = jsonParser.readJSON();

            serverLogger.log(Level.INFO, "Listening on port " + serverInformation.getPort() + " ...");
            serverSocket = new ServerSocket(serverInformation.getPort());
            while (true) {
                socket = serverSocket.accept();

                serverLogger.log(Level.INFO, "Client connect to server successfully.");
                System.out.println("just Connected to server.");
                Transaction transaction;
                ArrayList<Deposit> depositArrayList = new ArrayList<Deposit>();
                String request;
//                do {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                request = (String) objectInputStream.readObject();
                serverLogger.log(Level.INFO, "Incoming request");
                //transactionsList.add(transaction);
                //System.out.println(" element of transactionList:" + transactionsList +"  "+ Thread.currentThread().getName());
                transaction = convertTransactionStrToTransactionObject(request);
                Deposit deposit = searchDeposit(transaction, serverInformation.getDeposits());

                if (deposit.getCustomer() != null) {
                    depositArrayList.add(deposit);
                    System.out.println(depositArrayList);
                    if (transaction.getAmount().compareTo(BigDecimal.ZERO) == 1) {
//                        synchronized (deposit) {
                            transaction.validateRequest(deposit);
                        if (transaction.getTerminalId() == 21374) {
                            Thread.sleep(2000);
                        }
                        if (transaction.getRequestIsValid()) {
                                serverLogger.log(Level.INFO, "Request is valid");
                                transaction.applyChanges(deposit);
                                serverLogger.log(Level.INFO, "Changes were applied");
                            } else {
                                serverLogger.log(Level.INFO, "Request is not valid");
                            }
//                        }
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
//                System.out.println(depositArrayList);
//                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Transaction convertTransactionStrToTransactionObject(String request) {
        String[] str = request.split(",");
        Integer transactionId = Integer.parseInt(str[0]);
        String transactionType = str[1];
        BigDecimal transactionAmount = new BigDecimal(str[2]);
        Integer transactionDeposit = Integer.parseInt(str[3]);
        return new Transaction(transactionId, transactionType, transactionAmount, transactionDeposit);
    }

    /*public boolean ValidateRequest(Terminal terminal) {
        boolean requestIsValid = true;
        for (int i = 0; i < terminal.getTransactions().size(); i++) {
            if (terminal.getTransactions().get(i).getType() != "deposit" ||
                    terminal.getTransactions().get(i).getType() != "withdraw") {
                requestIsValid = false;
            }
            if ((terminal.getTransactions().get(i).getAmount()).compareTo(new BigDecimal(0)) == -1) {
                requestIsValid = false;
            }
        }

        return requestIsValid;
    }*/

    /*public ArrayList<Deposit> applyChangeToDeposits(Terminal terminal) throws IOException, ParseException {
        ArrayList<Transaction> listOfTransactions = new ArrayList<Transaction>();
        listOfTransactions=terminal.getTransactions();
        JsonParser jsonParser = new JsonParser();
        ServerInformation serverInformation=jsonParser.readJSON();
        List<Deposit> listOfDeposits = new ArrayList<Deposit>();
        listOfDeposits=serverInformation.getDeposits();
        ArrayList<Deposit> listOfDepositsResult = new ArrayList<Deposit>();


        for(int i=0;i<listOfTransactions.size();i++){
            for (int j=0;j<listOfDeposits.size();j++){
                if(listOfTransactions.get(i).getDeposit() == listOfDeposits.get(j).getId()){
                        listOfDeposits.get(j).setInitialBalance(listOfTransactions.get(i).getAmount());
                        listOfDepositsResult.add(listOfDeposits.get(j));
                }
            }
        }
        return listOfDepositsResult;
        }*/

            /*Terminal terminal;

            JsonParser jsonParser = new JsonParser();
            ServerInformation serverInformation = jsonParser.readJSON();
            ServerSocket listener = new ServerSocket(serverInformation.getPort());
            Socket socket = listener.accept();

            ObjectInputStream serverInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream serverOutputStream = new ObjectOutputStream(socket.getOutputStream());

            terminal = (Terminal) serverInputStream.readObject();

            for (int i=0;i<terminal.getTransactions().size();i++){
                for (int j=0;j<serverInformation.getDeposits().size();j++){
                    if (terminal.getTransactions().get(i).getDeposit() == serverInformation.getDeposits().get(j).getId()){
                        serverInformation.getDeposits().get(j).setInitialBalance(terminal.getTransactions().get(i).getDeposit());
                        System.out.println("YOHO");
                    }
                }
            }

    serverOutputStream.writeObject(terminal);

            serverInputStream.close();
            serverOutputStream.close();

            socket.close();
            listener.close();*/

}
