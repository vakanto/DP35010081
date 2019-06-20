package ha07_ha08.Shop;

import com.sun.net.httpserver.HttpExchange;
import org.fulib.yaml.EventFiler;
import org.fulib.yaml.EventSource;
import org.fulib.yaml.Yamler;
import sun.awt.image.ImageWatched;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class WarehouseProxy {

    private final String GET_SHOP_EVENTS_URL="http://127.0.0.1:5002/getShopEvents";

    private EventSource eventSource;
    private EventFiler eventFiler;
    private ScheduledExecutorService executorService;
    private Executor executor;
    public ShopBuilder shopBuilder;


    public WarehouseProxy(ShopBuilder shopBuilder){
        this.shopBuilder=shopBuilder;
        this.eventSource=new EventSource();
        this.eventFiler = new EventFiler(eventSource)
                .setHistoryFileName("src/main/java/ha07_ha08/database/WarehouseProxy.yml");
        executorService = Executors.newSingleThreadScheduledExecutor();
        executor = Executors.newSingleThreadExecutor();

        String history = eventFiler.loadHistory();
        if(history!=null){
            ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(history);
            eventSource.append(history);
        }

        eventFiler.storeHistory();
        eventFiler.startEventLogging();

    }

    public void getWarehouseEvents(String lastKnownWarhouseEventTime){
        String warehouseEvents = sendRequest(GET_SHOP_EVENTS_URL, "lastKnown" + lastKnownWarhouseEventTime);
        ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(warehouseEvents);
        executor.execute(()-> shopBuilder.applyEvents(events));
    }

    private static String sendRequest(String url, String lastEvents) {
        try {
            URL requestUrl = new URL(url);
            URLConnection connection = requestUrl.openConnection();
            HttpURLConnection urlConnection = (HttpURLConnection) connection;
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            byte[] output = lastEvents.getBytes((StandardCharsets.UTF_8));
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
}
