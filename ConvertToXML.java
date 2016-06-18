import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ConvertToXML {
    public static final String xmlFilePath = "src\\main\\resources\\result.xml";

    public void dynamicConvert(ArrayList<ResponseXMLFields> responseXMLFieldsArrayList) throws IOException {

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder;
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFilePath);
            Element root = document.getDocumentElement();

            for (ResponseXMLFields responseXMLFields:responseXMLFieldsArrayList){
                if (responseXMLFields.getTypeOfResponse() ==0){
                    Element response = document.createElement("response");

                    Element transaction = document.createElement("transaction");
                    Element deposit = document.createElement("deposit");

                    Attr transactionIdAttr = document.createAttribute("id");
                    Attr transactionTypeAttr = document.createAttribute("type");

                    transactionIdAttr.setValue(String.valueOf(responseXMLFields.getTransactionId()));
                    transactionTypeAttr.setValue(responseXMLFields.getTransactionType());

                    transaction.setAttributeNode(transactionIdAttr);
                    transaction.setAttributeNode(transactionTypeAttr);

                    Attr depositIdAttr = document.createAttribute("id");
                    Attr depositAmountAttr = document.createAttribute("amount");

                    depositIdAttr.setValue(String.valueOf(responseXMLFields.getDepositId()));
                    depositAmountAttr.setValue(String.valueOf(responseXMLFields.getDepositAmount()));

                    deposit.setAttributeNode(depositIdAttr);
                    deposit.setAttributeNode(depositAmountAttr);

                    response.appendChild(transaction);
                    response.appendChild(deposit);

                    root.appendChild(response);
                }
                else if(responseXMLFields.getTypeOfResponse() ==1){
                    Element response = document.createElement("response");

                    Element transaction = document.createElement("transaction");
                    Element message = document.createElement("message");

                    Attr transactionIdAttr = document.createAttribute("id");
                    Attr transactionTypeAttr = document.createAttribute("type");

                    transactionIdAttr.setValue(String.valueOf(responseXMLFields.getTransactionId()));
                    transactionTypeAttr.setValue(responseXMLFields.getTransactionType());

                    transaction.setAttributeNode(transactionIdAttr);
                    transaction.setAttributeNode(transactionTypeAttr);

                    Attr messageAttr = document.createAttribute("text");
                    messageAttr.setValue("Fail: Invalid deposit ID.");
                    message.setAttributeNode(messageAttr);

                    response.appendChild(transaction);
                    response.appendChild(message);

                    root.appendChild(response);

                }
                else if(responseXMLFields.getTypeOfResponse() == -1){
                    Element response = document.createElement("response");

                    Element transaction = document.createElement("transaction");
                    Element message = document.createElement("message");

                    Attr transactionIdAttr = document.createAttribute("id");
                    Attr transactionTypeAttr = document.createAttribute("type");

                    transactionIdAttr.setValue(String.valueOf(responseXMLFields.getTransactionId()));
                    transactionTypeAttr.setValue(responseXMLFields.getTransactionType());

                    transaction.setAttributeNode(transactionIdAttr);
                    transaction.setAttributeNode(transactionTypeAttr);

                    Attr messageAttr = document.createAttribute("text");
                    messageAttr.setValue("Fail: transaction amount is negative.");
                    message.setAttributeNode(messageAttr);

                    response.appendChild(transaction);
                    response.appendChild(message);

                    root.appendChild(response);
                }
            }

            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(xmlFilePath);
            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
    /*public void convert(*//*int transactionId, String transactionType, int depositId, BigDecimal depositAmount, ArrayList<Integer> typeOfResponses*//*) throws IOException, ClassNotFoundException {

        Client client = new Client();
        ArrayList<String> responseList= client.getResponse();
        for (int i=0;i<responseList.size();i++){
            System.out.println(responseList.get(i) + "      xx");
        }

        *//*try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("responses");
            document.appendChild(root);

            for (int i=0;i<typeOfResponses.size();i++){
                if (typeOfResponses.get(i) == 0){
                    Element response = document.createElement("response");
                    root.appendChild(response);
                    // transaction & deposit element
                    Element transaction = document.createElement("transaction");
                    Element deposit = document.createElement("deposit");

                    response.appendChild(transaction);
                    response.appendChild(deposit);

                    Attr transactionIdAttr = document.createAttribute("id");
                    Attr transactionTypeAttr = document.createAttribute("type");

                    transactionIdAttr.setValue(String.valueOf(transactionId));
                    transactionTypeAttr.setValue(transactionType);

                    transaction.setAttributeNode(transactionIdAttr);
                    transaction.setAttributeNode(transactionTypeAttr);

                    Attr depositIdAttr = document.createAttribute("id");
                    Attr depositAmountAttr = document.createAttribute("amount");

                    depositIdAttr.setValue(String.valueOf(depositId));
                    depositAmountAttr.setValue(String.valueOf(depositAmount));

                    deposit.setAttributeNode(depositIdAttr);
                    deposit.setAttributeNode(depositAmountAttr);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();

                    Transformer transformer = transformerFactory.newTransformer();

                    DOMSource domSource = new DOMSource(document);

                    StreamResult streamResult = new StreamResult(new File(xmlFilePath));


                    transformer.transform(domSource, streamResult);

                    System.out.println("Done creating XML File");
                }
                if (typeOfResponses.get(i) == 1){
                    Element response = document.createElement("response");
                    root.appendChild(response);

                    Element transaction = document.createElement("transaction");
                    Element deposit = document.createElement("deposit");
                    response.appendChild(transaction);
                    root.appendChild(deposit);

                    Attr transactionIdAttr = document.createAttribute("id");
                    transactionIdAttr.setValue(String.valueOf(transactionId));
                    transaction.setAttributeNode(transactionIdAttr);

                    Attr depositIdAttr = document.createAttribute("id");
                    Attr depositFailureMessage = document.createAttribute("message");

                    depositIdAttr.setValue(String.valueOf(depositId));
                    depositFailureMessage.setValue("Fail: Invalid deposit ID.");

                    deposit.setAttributeNode(depositIdAttr);
                    deposit.setAttributeNode(depositFailureMessage);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();

                    Transformer transformer = transformerFactory.newTransformer();

                    DOMSource domSource = new DOMSource(document);

                    StreamResult streamResult = new StreamResult(new File(xmlFilePath));


                    transformer.transform(domSource, streamResult);

                    System.out.println("Done creating XML File");

                }
                if (typeOfResponses.get(i) == -1){
                    Element response = document.createElement("response");
                    root.appendChild(response);

                    Element transaction = document.createElement("transaction");
                    Element deposit = document.createElement("deposit");
                    response.appendChild(transaction);
                    root.appendChild(deposit);

                    Attr transactionIdAttr = document.createAttribute("id");
                    transactionIdAttr.setValue(String.valueOf(transactionId));
                    transaction.setAttributeNode(transactionIdAttr);

                    Attr depositIdAttr = document.createAttribute("id");
                    Attr depositFailureMessage = document.createAttribute("message");

                    depositIdAttr.setValue(String.valueOf(depositId));
                    depositFailureMessage.setValue("Fail: transaction amount is negative.");

                    deposit.setAttributeNode(depositIdAttr);
                    deposit.setAttributeNode(depositFailureMessage);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();

                    Transformer transformer = transformerFactory.newTransformer();

                    DOMSource domSource = new DOMSource(document);

                    StreamResult streamResult = new StreamResult(new File(xmlFilePath));


                    transformer.transform(domSource, streamResult);

                    System.out.println("Done creating XML File");
                }
            }
       } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }*//*
    }*/

    public void convert(ArrayList<ResponseXMLFields> responseXMLFieldsArrayList){
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = null;
            documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            // root element
            Element root = document.createElement("response");
            document.appendChild(root);

            for (int i=0;i<responseXMLFieldsArrayList.size();i++){
                if (responseXMLFieldsArrayList.get(i).getTypeOfResponse() == 0){
                    ResponseXMLFields responseXMLFields = responseXMLFieldsArrayList.get(i);
                    Element response = document.createElement("response");
                    root.appendChild(response);
                    // transaction & deposit element
                    Element transaction = document.createElement("transaction");
                    Element deposit = document.createElement("deposit");

                    response.appendChild(transaction);
                    response.appendChild(deposit);

                    Attr transactionIdAttr = document.createAttribute("id");
                    Attr transactionTypeAttr = document.createAttribute("type");

                    transactionIdAttr.setValue(String.valueOf(responseXMLFields.getTransactionId()));
                    transactionTypeAttr.setValue(responseXMLFields.getTransactionType());

                    transaction.setAttributeNode(transactionIdAttr);
                    transaction.setAttributeNode(transactionTypeAttr);

                    Attr depositIdAttr = document.createAttribute("id");
                    Attr depositAmountAttr = document.createAttribute("amount");

                    depositIdAttr.setValue(String.valueOf(responseXMLFields.getDepositId()));
                    depositAmountAttr.setValue(String.valueOf(responseXMLFields.getDepositAmount()));

                    deposit.setAttributeNode(depositIdAttr);
                    deposit.setAttributeNode(depositAmountAttr);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();

                    Transformer transformer = transformerFactory.newTransformer();

                    DOMSource domSource = new DOMSource(document);

                    StreamResult streamResult = new StreamResult(xmlFilePath);


                    transformer.transform(domSource, streamResult);
                }
                else if (responseXMLFieldsArrayList.get(i).getTypeOfResponse() == 1){
                    ResponseXMLFields responseXMLFields = responseXMLFieldsArrayList.get(i);
                    Element response = document.createElement("response");
                    root.appendChild(response);

                    Element transaction = document.createElement("transaction");
                    Element deposit = document.createElement("deposit");
                    response.appendChild(transaction);
                    root.appendChild(deposit);

                    Attr transactionIdAttr = document.createAttribute("id");
                    transactionIdAttr.setValue(String.valueOf(responseXMLFields.getTransactionId()));
                    transaction.setAttributeNode(transactionIdAttr);

                    Attr depositIdAttr = document.createAttribute("id");
                    Attr depositFailureMessage = document.createAttribute("message");

                    depositIdAttr.setValue(String.valueOf(responseXMLFields.getDepositId()));
                    depositFailureMessage.setValue("Fail: Invalid deposit ID.");

                    deposit.setAttributeNode(depositIdAttr);
                    deposit.setAttributeNode(depositFailureMessage);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();

                    Transformer transformer = transformerFactory.newTransformer();

                    DOMSource domSource = new DOMSource(document);

                    StreamResult streamResult = new StreamResult(xmlFilePath);


                    transformer.transform(domSource, streamResult);
                }
                else if (responseXMLFieldsArrayList.get(i).getTypeOfResponse() == -1){
                    ResponseXMLFields responseXMLFields = responseXMLFieldsArrayList.get(i);
                    Element response = document.createElement("response");
                    root.appendChild(response);

                    Element transaction = document.createElement("transaction");
                    Element deposit = document.createElement("deposit");
                    response.appendChild(transaction);
                    root.appendChild(deposit);

                    Attr transactionIdAttr = document.createAttribute("id");
                    transactionIdAttr.setValue(String.valueOf(responseXMLFields.getTransactionId()));
                    transaction.setAttributeNode(transactionIdAttr);

                    Attr depositIdAttr = document.createAttribute("id");
                    Attr depositFailureMessage = document.createAttribute("message");

                    depositIdAttr.setValue(String.valueOf(responseXMLFields.getDepositId()));
                    depositFailureMessage.setValue("Fail: transaction amount is negative.");

                    deposit.setAttributeNode(depositIdAttr);
                    deposit.setAttributeNode(depositFailureMessage);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();

                    Transformer transformer = transformerFactory.newTransformer();

                    DOMSource domSource = new DOMSource(document);

                    StreamResult streamResult = new StreamResult(xmlFilePath);


                    transformer.transform(domSource, streamResult);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }



    public  void convertToXml(int transactionId , String transactionType, int depositId, BigDecimal depositAmount) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            // root element
            Element root = document.createElement("response");
            document.appendChild(root);

            // transaction & deposit element
            Element transaction = document.createElement("transaction");
            Element deposit = document.createElement("deposit");

            root.appendChild(transaction);
            root.appendChild(deposit);

            // set an attribute

            Attr transactionIdAttr = document.createAttribute("id");
            Attr transactionTypeAttr = document.createAttribute("type");

            transactionIdAttr.setValue(String.valueOf(transactionId));
            transactionTypeAttr.setValue(transactionType);

            transaction.setAttributeNode(transactionIdAttr);
            transaction.setAttributeNode(transactionTypeAttr);

            Attr depositIdAttr = document.createAttribute("id");
            Attr depositAmountAttr = document.createAttribute("amount");

            depositIdAttr.setValue(String.valueOf(depositId));
            depositAmountAttr.setValue(String.valueOf(depositAmount));

            deposit.setAttributeNode(depositIdAttr);
            deposit.setAttributeNode(depositAmountAttr);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();

            DOMSource domSource = new DOMSource(document);

            StreamResult streamResult = new StreamResult(new File(xmlFilePath));


            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {

            pce.printStackTrace();

        } catch (TransformerException tfe) {

            tfe.printStackTrace();

        }
    }
}
