package ha07_ha08.Warehouse;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.fulib.yaml.Yamler;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class WarehouseServer {

    public static WareHouseBuilder wareHouseBuilder;

    public WarehouseServer() throws IOException, UnirestException {

    }

    private static int orderProduct(Request request, Response response) throws IOException, UnirestException {
        String message = request.body();
        ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(message);
        wareHouseBuilder.applyEvents(events, 0);
        return 0;
    }

    public static void main(String [] args) {

        Spark.stop();
        Spark.ipAddress("127.0.0.1");
        Spark.port(5002);
        Spark.post("/orderProduct", (request, response) -> orderProduct(request, response));
        Spark.post("/getShopEvents", (request, response) -> getEvents(request, response));
        Spark.post("/getWarehouseEvents", (request, response) -> getWarehouseEvents(request, response));

        try {
            wareHouseBuilder= new WareHouseBuilder();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private static String getWarehouseEvents(Request request, Response response) throws IOException, UnirestException {
        String message = request.body();
        ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(message);
        String responseString = wareHouseBuilder.applyEvents(events, 1);
        return responseString;
    }

    private static String getEvents(Request request, Response response) throws IOException, UnirestException {
        String message = request.body();
        ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(message);
        String responseYaml = wareHouseBuilder.applyEvents(events, 0);
        return responseYaml;
    }
}
