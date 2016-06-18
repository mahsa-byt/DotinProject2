import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {
    String threadName;
    String xmlFilePath;
    Socket socket;
    Transaction transaction;
    private String response;
    private String outLogpath;
    private final static Logger clientLogger = Logger.getLogger(Client.class.getName());

    public Client(String xmlFilePathParameter) {
        xmlFilePath = xmlFilePathParameter;
        threadName = xmlFilePathParameter;
    }

    public Client() {

    }

    public Terminal readXmlFile(String xmlFilePathParameter) {
        XMLParser xmlParser = new XMLParser();
        Terminal terminal = new Terminal();
        try {
            terminal = xmlParser.readXMLFile(xmlFilePathParameter);
            outLogpath = terminal.getOutLogPath();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return terminal;
    }

    public Socket connectToServer(String serverIP, int port) {
        try {
            socket = new Socket(serverIP, port);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail To Connect!!");
        }
        return socket;
    }

    public void sendTransactions(ArrayList<Transaction> transactions) throws IOException {
        for (Transaction transaction : transactions) {
            String request = transaction.transactionToString();
            ObjectOutputStream clientObjectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            clientObjectOutputStream.writeObject(request);
        }
    }

    public void getResponse() throws IOException, ClassNotFoundException {
        ObjectInputStream clientObjectInputStream = new ObjectInputStream(socket.getInputStream());
        response = (String) clientObjectInputStream.readObject();
        System.out.println(Thread.currentThread().getName() + " get the response from server successfully.");
        ArrayList<String> responseList = new ArrayList<String>();
        responseList.add(response);

        ArrayList<ResponseXMLFields> responseXMLFieldsArrayList = new ArrayList<ResponseXMLFields>();
        responseXMLFieldsArrayList = match(response);
        ConvertToXML convertToXML = new ConvertToXML();
        // convertToXML.convert(responseXMLFieldsArrayList);
       // convertToXML.dynamicConvert(responseXMLFieldsArrayList);

        System.out.println("responselist after execution of " + Thread.currentThread().getName() +" :  " + responseList);
    }


    public ArrayList<ResponseXMLFields> match(String response) {
        ResponseXMLFields responseXMLFields = new ResponseXMLFields();
        ArrayList<ResponseXMLFields> responseXMLFieldsArrayList = new ArrayList<ResponseXMLFields>();
        Integer result;
        if (response.matches("([0-9])*,([0-9])*,([a-z])*,([0-9])*,([0-9])*")) {
            result = 0;
            String[] str = response.split(",");

            responseXMLFields.setDepositId(Integer.valueOf(str[0]));
            responseXMLFields.setTransactionId(Integer.valueOf(str[1]));
            responseXMLFields.setTransactionType(str[2]);
            responseXMLFields.setDepositAmount(new BigDecimal(str[3]));
            responseXMLFields.setTypeOfResponse(result);
            responseXMLFieldsArrayList.add(responseXMLFields);

        } else if (response.matches("([0-9])*,Fail: transaction amount is negative.")) {
            result = -1;
            responseXMLFields.setTypeOfResponse(result);
            responseXMLFields.setMessage("Fail: transaction amount is negative.");
            responseXMLFieldsArrayList.add(responseXMLFields);
        } else if (response.matches("([0-9])*,Fail: Invalid deposit ID.")) {
            result = 1;
            responseXMLFields.setTypeOfResponse(result);
            responseXMLFields.setMessage("Fail:Invalid deposit ID.");
            responseXMLFieldsArrayList.add(responseXMLFields);
        } else
            result = 2;

        return responseXMLFieldsArrayList;
    }


    @Override
    public void run() {
        Terminal resultTerminal;
        resultTerminal = readXmlFile(xmlFilePath);
        clientLogger.log(Level.INFO, Thread.currentThread().getName() +" Parsed XMLFile successfully.");
        connectToServer(resultTerminal.getServerIP(), resultTerminal.getServerPort());
        clientLogger.log(Level.INFO, Thread.currentThread().getName()+ " connect to server");


        try {
            FileHandler handler = new FileHandler(outLogpath, true);
            clientLogger.addHandler(handler);

            sendTransactions(resultTerminal.getTransactions());
            clientLogger.log(Level.INFO, Thread.currentThread().getName()+ " sent the Request successfully");
            do {
                getResponse();
            } while (response != null);
            clientLogger.log(Level.INFO, Thread.currentThread().getName() +" received Response  successfully");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    void randomWait() {
        try {
            Thread.currentThread().sleep((long) (3000 * Math.random()));
        } catch (InterruptedException x) {
            System.out.println("Interrupted!");
        }
    }

    /*public synchronized void connectToServer()  {

        XMLParser xmlParser = new XMLParser();
        Terminal terminal = new Terminal();
        ArrayList<Deposit> clientDepositList =  new ArrayList<Deposit>();
        try {
            terminal=xmlParser.readXMLFile("C:\\Users\\DotinSchool2\\Desktop\\DotinProject2\\src\\main\\resources\\"+threadName);
            Socket clientSocket = new Socket(terminal.getServerIP(),terminal.getServerPort());
            ObjectOutputStream clientOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream clientInputStream = new ObjectInputStream(clientSocket.getInputStream());
            clientOutputStream.writeObject(terminal);

            System.out.println("Succeessful");
            clientDepositList = (ArrayList<Deposit>) clientInputStream.readObject();
            clientOutputStream.close();
            clientInputStream.close();
            clientSocket.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }*/

        /*try {
            XMLParser xmlParser = new XMLParser();
            Terminal terminal = xmlParser.readXMLFile();

            socket = new Socket(terminal.getServerIP(), terminal.getServerPort());

            ObjectOutputStream clientOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream clientInputStream = new ObjectInputStream(socket.getInputStream());
            clientOutputStream.writeObject(terminal);

            terminal = (Terminal) clientInputStream.readObject();

            for (int i = 0; i < terminal.getTransactions().size(); i++) {
                System.out.println(terminal.getTransactions().get(i).getAmount());
            }

            clientInputStream.close();
            clientOutputStream.close();

            socket.close();
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/


}
