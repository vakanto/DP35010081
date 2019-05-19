package ha05;

import ha05.customer_client.CommunicationProxy;
import ha05.customer_client.Customer_Client;
import ha05.customer_client.MqttCallBack;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class test_ha05 {


    @Test
    public void checkConfigFile(){
        File file = new File("src/main/resources/mosquitto.conf");
        Assert.assertTrue(file.exists());
    }

    @Test
    public void testServer(){
        try {
            Process p = Runtime.getRuntime().exec("mosquitto -c " + "src/main/resources/mosquitto.conf");
            //Process q = Runtime.getRuntime().exec("mosquitto_pub -t test -p 2000 -m test");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject();
                jsonObject.put("Receiver", "test");
                jsonObject.put("Message", "test");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CommunicationProxy proxy = new CommunicationProxy();

            Process r=null;
            try {
                MqttClient client =  new MqttClient("tcp://127.0.0.1:2000", MqttClient.generateClientId());
                MqttCallBack mqttCallBack= new MqttCallBack();

                client.setCallback(mqttCallBack);
                client.connect();
                client.subscribe("test");

                Assert.assertTrue(client.isConnected());

                proxy.sendMessage(jsonObject);

                TimeUnit.SECONDS.sleep(1);

                Assert.assertEquals(jsonObject.toString(), mqttCallBack.getLastMessage());

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
