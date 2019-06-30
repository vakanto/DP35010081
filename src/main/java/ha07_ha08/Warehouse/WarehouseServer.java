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

    public WarehouseServer() throws IOException {

    }

    private static int orderProduct(Request request, Response response) throws IOException {
        String message = request.body();
        ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(message);
        wareHouseBuilder.applyEvents(events, 0);
        return 0;
    }

    public static void main(String [] args) {
        org.apache.log4j.BasicConfigurator.configure();
        Spark.stop();
        Spark.ipAddress("0.0.0.0");
        Spark.port(5002);
        Spark.post("/orderProduct", (request, response) -> orderProduct(request, response));
        Spark.post("/getShopEvents", (request, response) -> getEvents(request, response));
        Spark.post("/getWarehouseEvents", (request, response) -> getWarehouseEvents(request, response));
        Spark.post("/addLot", (request, response) -> addLot(request, response));
        Spark.get("/heartbeat", (request, response) -> answerHeartbeat(request, response));

        try {
            wareHouseBuilder= new WareHouseBuilder();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String answerHeartbeat(Request request, Response response) {
        System.out.println("Hearbeat request received");
        response.body("pretty_damn_well");
        return "pretty_damn_well";
    }

    private static Object addLot(Request request, Response response) throws IOException {
        System.out.println("Received addLot post event");
        String message = request.body();
        ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(message);
        String responseString = wareHouseBuilder.applyEvents(events, 0);
        return responseString;
    }

    private static String getWarehouseEvents(Request request, Response response) throws IOException, UnirestException {
        String message = request.body();
        System.out.println("Received getWarehouseEvents post event");
        System.out.println("WarehouseServer received " + message);
        ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(message);
        String responseString = wareHouseBuilder.applyEvents(events, 1);
        System.out.println("Warehouse responses with " + responseString);
        return responseString;
    }

    private static String getEvents(Request request, Response response) throws IOException, UnirestException {
        String message = request.body();
        ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(message);
        String responseYaml = wareHouseBuilder.applyEvents(events, 0);
        return responseYaml;
    }
}
