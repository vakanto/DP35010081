package ha07_ha08.Warehouse;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.fulib.yaml.Yamler;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static spark.Spark.port;
import static spark.Spark.post;


public class WarehouseServer {

    public static WareHouseBuilder wareHouseBuilder;

    public WarehouseServer() throws IOException, UnirestException {

    }

    private static int orderProduct(Request request, Response response) throws IOException, UnirestException {
        String message = request.body();

        ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(message);
        wareHouseBuilder.applyEvents(events);
        return 0;
    }

    public static void main(String [] args) {
        try {
            wareHouseBuilder=new WareHouseBuilder();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        port(5002);
        post("/orderProduct", (request, response) -> orderProduct(request, response));
    }
}
