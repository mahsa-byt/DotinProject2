import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket socket;
        try {
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
        }
    }
}
