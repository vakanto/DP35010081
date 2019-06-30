package ha09;

import org.fulib.yaml.EventSource;
import org.junit.Assert;
import org.junit.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
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
        File composeFile = new File("src/main/java/ha09/docker-compose.yml");
        Assert.assertTrue(composeFile.exists());
        sleep(2000);
        String ip = compose.getServiceHost("shopserver", 5001);
        Assert.assertEquals("localhost", ip);
        String address = "http://" + compose.getServiceHost("warehouseserver", 5002) + ":" + compose.getServicePort("warehouseserver", 5002);
        LinkedHashMap<String,String> event = new LinkedHashMap<>();
        event.put("event_type", "add_product_to_shop");
        event.put(".eventKey", "lot1");
        event.put("lotID", "lot1");
        event.put("product_name", "Schuhe");
        event.put("itemCount", "" + "20");

        System.out.println(sendRequest(EventSource.encodeYaml(event), "/addLot", 5002));
        //String response = simpleGetRequest(address);
        compose.stop();
    }

    public String sendRequest(String yaml, String targetUrl, int servicePort){

        try {
            URL url = new URL("http://127.0.0.1:" + servicePort + "/" + targetUrl);
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

}
