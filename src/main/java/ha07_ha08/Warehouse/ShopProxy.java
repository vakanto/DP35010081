package ha07_ha08.Warehouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.fulib.yaml.EventFiler;
import org.fulib.yaml.EventSource;
import org.fulib.yaml.Yamler;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.*;

public class ShopProxy {

    private EventSource eventSource;
    private EventFiler eventFiler;
    private ScheduledExecutorService executorService;

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
        sendRequest(yaml);
    }

    public void sendRequest(String yaml) throws UnirestException {

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

            InputStream inputStream = http.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = bufferedReader.readLine();

            bufferedReader.close();


        } catch (Exception e) {
            executorService.schedule(()-> {
                try {
                    sendRequest(yaml);
                } catch (UnirestException ex) {
                    ex.printStackTrace();
                }
            }, 10, TimeUnit.SECONDS);
        }
    }
}
