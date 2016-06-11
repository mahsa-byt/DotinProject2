import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class XMLParser implements Serializable{
    public Terminal readXMLFile() throws FileNotFoundException, XMLStreamException {

        Integer transactionAmount = null;
        Integer transactionDeposit = null;
        Integer transactionID = null;
        String transactionType = null;

        File xmlFile = new File("C:\\Users\\DotinSchool2\\Desktop\\DotinProject2\\src\\main\\resources\\terminal.xml");

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(xmlFile));

        Terminal terminal = new Terminal();
        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        Transaction transaction = new Transaction();

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    Iterator<Attribute> attributes;
                    if ("terminal".equalsIgnoreCase(qName)) {
                        attributes = startElement.getAttributes();
                        terminal.setId(Integer.valueOf(attributes.next().getValue()));
                        terminal.setTerminalType(attributes.next().getValue());
                    } else if ("server".equalsIgnoreCase(qName)) {
                        attributes = startElement.getAttributes();
                        terminal.setServerPort(Integer.valueOf(attributes.next().getValue().trim()));
                        terminal.setServerIP(attributes.next().getValue());
                    } else if ("outlog".equalsIgnoreCase(qName)) {
                        attributes = startElement.getAttributes();
                        terminal.setOutLogPath(attributes.next().getValue());
                    } else if ("transaction".equalsIgnoreCase(qName)) {
                        attributes = startElement.getAttributes();
                        /*transaction.setAmount(Integer.valueOf(attributes.next().getValue().trim().replace(",", "")));
                        System.out.println("amount " +transaction.getAmount());
                        transaction.setDeposit(Integer.valueOf(attributes.next().getValue()));
                        transaction.setId(Integer.valueOf(attributes.next().getValue()));
                        transaction.setType(attributes.next().getValue());
                        transactionList.add(transaction);*/
                        transactionAmount = Integer.parseInt(attributes.next().getValue().trim().replace(",", ""));
                        transactionDeposit = Integer.valueOf(attributes.next().getValue());
                        transactionID = Integer.valueOf(attributes.next().getValue());
                        transactionType = attributes.next().getValue();
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equalsIgnoreCase("transaction")) {
 //                       transactionList.add(transaction);
                        transactionList.add(new Transaction(transactionID,transactionType,transactionAmount,transactionDeposit));
                    }
                    break;
            }
        }
        terminal.setTransactions(transactionList);
        return terminal;
    }

    public XMLParser(){}
}
