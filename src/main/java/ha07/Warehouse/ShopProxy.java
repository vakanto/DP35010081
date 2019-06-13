package ha07.Warehouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.fulib.yaml.EventSource;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.concurrent.*;

public class ShopProxy {

    private EventSource eventSource;
    private ScheduledExecutorService executorService;

    public ShopProxy(){
        this.eventSource=new EventSource();
        executorService = Executors.newSingleThreadScheduledExecutor();

    }

    public void addProductToShop(LinkedHashMap<String,String> event) throws IOException, UnirestException {

        JSONObject eventJson = new JSONObject(event);
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode eventJsonNode =  new JsonNode(eventJson.toString());

        sendRequest(eventJsonNode);
    }

    public void sendRequest(JsonNode eventJson) throws UnirestException {

        try {
            HttpResponse<JsonNode> response = Unirest.post("http://127.0.0.1:5001/postEvent")
                    .header("accept", "application/json")
                    .body(eventJson)
                    .asJson();
            /**.asJsonAsync(new Callback<JsonNode>() {
                @Override
                public void completed(HttpResponse<JsonNode> response) {
                    int responseCode=response.getCode();
                    JsonNode body = response.getBody();
                }

                @Override
                public void failed(UnirestException e) {
                    System.out.println("The request failed");
                }

                @Override
                public void cancelled() {
                    System.out.println("Request was cancelled.");
                }
            });**/} catch (UnirestException e) {
            executorService.schedule(()-> {
                try {
                    sendRequest(eventJson);
                } catch (UnirestException ex) {
                    ex.printStackTrace();
                }
            }, 10, TimeUnit.SECONDS);
        }
    }
}
