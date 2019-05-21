package ha05.customer_client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class test_ha05_no_ui {
    @Test
    public void checkConfigFile(){
        File file = new File("src/main/resources/mosquitto.conf");
        Assert.assertTrue(file.exists());
        File taxiClientFXML = new File("src/main/resources/ha05.taxi_client/Taxi_Client.fxml");
        Assert.assertTrue(taxiClientFXML.exists());
    }

    @Test
    public void testServer(){
        try {
            Process p = Runtime.getRuntime().exec("mosquitto -c " + "src/main/resources/mosquitto.conf");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CommunicationProxy proxy = new CommunicationProxy(null, null, null);
            //Process q = Runtime.getRuntime().exec("mosquitto_pub -t test -p 2000 -m test");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject();
                jsonObject.put("receiver", "toTransport");
                jsonObject.put("name", "Thea");
                jsonObject.put("price", "12 Euro");
                jsonObject.put("time", "12:07 Uhr");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Process r=null;
            try {
                MqttClient client =  new MqttClient("tcp://127.0.0.1:2000", MqttClient.generateClientId());

                client.setCallback(proxy);
                client.connect();
                client.subscribe("toTransport");

                Assert.assertTrue(client.isConnected());

                proxy.sendMessage(jsonObject);

                TimeUnit.SECONDS.sleep(2);

                Assert.assertEquals(jsonObject.toString(),proxy.getLastMessage().toString());

            } catch (MqttException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            p.destroy();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
