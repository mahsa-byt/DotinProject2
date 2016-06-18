import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class JsonParser {

    public ServerInformation readJSON() throws IOException, ParseException {
        JSONParser jsonParser= new JSONParser();
        Object object= jsonParser.parse(new FileReader("src\\main\\resources\\core.json"));
        JSONObject jsonObject = (JSONObject) object;
        ServerInformation serverInformation = new ServerInformation();

        JSONArray jsonArray = (JSONArray) jsonObject.get("deposits");

        ArrayList<Deposit> listOfObjects=new ArrayList<Deposit>();
        Iterator iterator = jsonArray.iterator();
        while (iterator.hasNext()){
            JSONObject jsonObj = (JSONObject) iterator.next();
            Deposit deposit = new Deposit();
            deposit.setCustomer(jsonObj.get("customer").toString());
            deposit.setId(Integer.parseInt( jsonObj.get("id").toString()));
            deposit.setInitialBalance(new BigDecimal(jsonObj.get("initialBalance").toString().replace("," , "")));
            deposit.setUpperBound(new BigDecimal(jsonObj.get("upperBound").toString().replace("," , "")));

            listOfObjects.add(deposit);
        }
        serverInformation.setPort(Integer.parseInt(jsonObject.get("port").toString()));
        serverInformation.setDeposits(listOfObjects);
        serverInformation.setOutLog((String) jsonObject.get("outLog"));
        return serverInformation;
    }

    public JsonParser(){}
}
