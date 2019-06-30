package ha09;

import org.fulib.yaml.EventSource;
import org.junit.Assert;
import org.junit.Test;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

import static java.lang.Thread.sleep;

public class test_ha09 {

    public static DockerComposeContainer compose =
            new DockerComposeContainer(new File("src/main/java/ha09/docker-compose.yml"))
                    .withExposedService("shopserver", 5001)
                    .withExposedService("warehouseserver", 5002)
                    .withLocalCompose(true);

    @Test
    public void createContainer() throws IOException, InterruptedException {
        compose.start();

        LinkedHashMap<String,String> event = new LinkedHashMap<>();
        File composeFile = new File("src/main/java/ha09/docker-compose.yml");
        Assert.assertTrue(composeFile.exists());

        String warehouseHeartbeat = sendGetRequest("heartbeat",  5002);
        Assert.assertTrue(warehouseHeartbeat.contains("pretty_damn_well"));
        event.put("event_type", "heartbeat");
        String shopHeartbeat = sendPostRequest(EventSource.encodeYaml(event), "postEvent", 5001);
        Assert.assertTrue(shopHeartbeat.contains("The_shop_feels_very_good"));

        event = new LinkedHashMap<>();
        event.put("event_type", "add_product_to_shop");
        event.put(".eventKey", "lot1");
        event.put("lotID", "lot1");
        event.put("product_name", "Schuhe");
        event.put("itemCount", "" + "20");

        String response = sendPostRequest(EventSource.encodeYaml(event), "addLot", 5002);
        Assert.assertTrue(response.contains("Schuhe"));
        Assert.assertTrue(response.contains("lot1"));

        event=new LinkedHashMap<>();
        event.put("event_type", "getEvents");
        event.put("timestamp", "1");
        String shopEvents = sendPostRequest(EventSource.encodeYaml(event), "postEvent", 5001);

        Assert.assertTrue(shopEvents.contains("add_product_to_shop"));
        Assert.assertTrue(shopEvents.contains("Schuhe"));
        Assert.assertTrue(shopEvents.contains("20"));

        compose.stop();
    }

    public String sendGetRequest(String targetUrl, int targetService){

        try {
            URL url = new URL("http://127.0.0.1:" + targetService + "/" + targetUrl);
            URLConnection connection = url.openConnection();
            HttpURLConnection http =(HttpURLConnection)connection;
            http.setRequestMethod("GET");
            http.setDoOutput(true);

            http.connect();

            InputStream inputStream = http.getInputStream();
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

        } catch (Exception e)
        {
        }
        return null;
    }

    public String sendPostRequest(String yaml, String targetUrl, int servicePort){

        try {
            URL url = new URL("http://127.0.0.1:" + servicePort + "/" + targetUrl);
            //URL url = new URL(address + targetUrl);
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
            sleep(2000);

            InputStream inputStream = http.getInputStream();
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

        } catch (Exception e)
        {
        }
        return null;
    }

}
