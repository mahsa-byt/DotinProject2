import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonParser {

    public ServerInformation readJSON() throws IOException, ParseException {
        JSONParser jsonParser= new JSONParser();
        Object object= jsonParser.parse(new FileReader("C:\\Users\\DotinSchool2\\Desktop\\DotinProject2\\src\\main\\resources\\core.json"));
        JSONObject jsonObject = (JSONObject) object;
        ServerInformation serverInformation = new ServerInformation();

        JSONArray jsonArray = (JSONArray) jsonObject.get("deposits");

        List<Deposit> listOfDeposits=new ArrayList<Deposit>();
        Iterator iterator = jsonArray.iterator();
        while (iterator.hasNext()){
            JSONObject jsonObj = (JSONObject) iterator.next();
            Deposit deposit = new Deposit();
            deposit.setCustomer(jsonObj.get("customer").toString());
            deposit.setId(Integer.parseInt( jsonObj.get("id").toString()));
            deposit.setInitialBalance(Integer.valueOf(jsonObj.get("initialBalance").toString().replace("," , "")));
            deposit.setUpperBound(Integer.valueOf(jsonObj.get("upperBound").toString().replace("," , "")));

            listOfDeposits.add(deposit);
        }
        /*for (int i=0;i<listOfDeposits.size();i++){
            System.out.println(listOfDeposits.get(i).getCustomer() + listOfDeposits.get(i).getUpperBound()
            + "   "+listOfDeposits.get(i).getInitialBalance());
        }*/
        serverInformation.setPort(Integer.valueOf(jsonObject.get("port").toString()));
        serverInformation.setDeposits(listOfDeposits);
        serverInformation.setOutLog((String) jsonObject.get("outLog"));
        return serverInformation;
    }

    public JsonParser(){}
}
