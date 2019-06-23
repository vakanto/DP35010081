package ha07_ha08.Shop;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.fulib.yaml.EventFiler;
import org.fulib.yaml.EventSource;
import org.fulib.yaml.Yamler;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class WarehouseProxy {

    private final String GET_WAREHOUSE_EVENTS_URL="http://127.0.0.1:5002/getWarehouseEvents";
    private final String ORDER_PRODUCT_URL="http://127.0.0.1:5002/orderProduct";
    private EventSource eventSource;
    private EventFiler eventFiler;
    public ShopBuilder shopBuilder;


    public WarehouseProxy(ShopBuilder shopBuilder){
        this.shopBuilder=shopBuilder;
        this.eventSource=new EventSource();
        this.eventFiler = new EventFiler(eventSource)
                .setHistoryFileName("src/main/java/ha07_ha08/database/WarehouseProxy.yml");

        String history = eventFiler.loadHistory();
        if(history!=null){
            ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(history);
            eventSource.append(history);
        }
        eventFiler.storeHistory();
        eventFiler.startEventLogging();
    }

    public String getWarehouseEvents(long lastEventTime){
        LinkedHashMap<String, String> event = new LinkedHashMap<>();
        event.put("event_type", "getEvents");
        event.put("timestamp", String.valueOf(lastEventTime));
        String yaml = EventSource.encodeYaml(event);
        String warehouseEvents = sendRequest(GET_WAREHOUSE_EVENTS_URL, yaml);
        return warehouseEvents;
    }

    private static String sendRequest(String url, String message) {
        try {
            URL requestUrl = new URL(url);
            URLConnection connection = requestUrl.openConnection();
            HttpURLConnection urlConnection = (HttpURLConnection) connection;
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            byte[] output = message.getBytes((StandardCharsets.UTF_8));
            int length = output.length;

            urlConnection.setFixedLengthStreamingMode(length);
            urlConnection.setRequestProperty("Content-Type", "application/yml, charset=UTF-8");

            urlConnection.connect();

            try(OutputStream outputStream = urlConnection.getOutputStream()){
                outputStream.write(output);
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();

            while(true) {
                String line = bufferedReader.readLine();
                if(line==null){
                    break;
                }
                response.append(line).append("\n");
            }
            bufferedReader.close();

            return response.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void orderProduct(LinkedHashMap<String,String> event) throws IOException, UnirestException {
        String productOrder = EventSource.encodeYaml(event);
        sendRequest(ORDER_PRODUCT_URL, productOrder);
    }
}
