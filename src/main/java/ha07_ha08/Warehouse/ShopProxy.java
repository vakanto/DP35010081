package ha07_ha08.Warehouse;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.fulib.yaml.EventFiler;
import org.fulib.yaml.EventSource;
import org.fulib.yaml.Yamler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.*;

public class ShopProxy {

    private EventSource eventSource;
    private EventFiler eventFiler;
    private ScheduledExecutorService executorService;
    private long lastConnectionTime;

    public ShopProxy(){
        this.eventSource=new EventSource();
        this.eventFiler = new EventFiler(eventSource)
            .setHistoryFileName("src/main/java/ha07_ha08/database/ShopProxy.yml");
        executorService = Executors.newSingleThreadScheduledExecutor();

        String history = eventFiler.loadHistory();
        if(history!=null){
            ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(history);
            eventSource.append(history);
        }

        eventFiler.storeHistory();
        eventFiler.startEventLogging();
    }

    public void addProductToShop(LinkedHashMap<String,String> event) throws IOException, UnirestException {
        String yaml = EventSource.encodeYaml(event);
        eventSource.append(event);
        sendRequest(yaml);
    }

    public void sendLoadedEvents(ArrayList<LinkedHashMap<String,String>> eventList) throws UnirestException {
        for(LinkedHashMap<String,String>event:eventList){
            String yaml = EventSource.encodeYaml(event);
            sendRequest(yaml);
        }
    }

    public void deleteShop(LinkedHashMap<String,String> event) throws UnirestException {
        String yaml = EventSource.encodeYaml(event);
        sendRequest(yaml);
    }

    public String sendRequest(String yaml) throws UnirestException {

        try {
            URL url = new URL("http://127.0.0.1:5001/postEvent");
            URLConnection connection = url.openConnection();
            HttpURLConnection http =(HttpURLConnection)connection;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            byte[] output = yaml.getBytes(StandardCharsets.UTF_8);
            int length = output.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type","application/yaml; charset=UTF-8");
            http.connect();

            try(OutputStream os = http.getOutputStream()){
                os.write(output);
            }
            lastConnectionTime = Instant.now().toEpochMilli();
            InputStream inputStream = http.getInputStream();
            lastConnectionTime = Instant.now().toEpochMilli();
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

        } catch (Exception e) {
        }
        return null;
    }

    public String loadEvents(long lastEventTime) throws UnirestException {
        LinkedHashMap<String, String> event = new LinkedHashMap<>();
        event.put("event_type", "getEvents");
        event.put("timestamp", String.valueOf(lastEventTime));
        String yaml = EventSource.encodeYaml(event);
        String response = sendRequest(yaml);
        return response;
    }
}
