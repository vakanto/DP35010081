package ha05.customer_client;

import ha05.customer_client.CommunicationProxy;
import ha05.customer_client.Customer_Client;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.testfx.framework.junit.ApplicationTest;


public class test_ha05 extends ApplicationTest {


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

                client.setCallback(proxy);
                client.connect();
                client.subscribe("test");

                Assert.assertTrue(client.isConnected());

                proxy.sendMessage(jsonObject);

                TimeUnit.SECONDS.sleep(1);

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

    private Customer_Client customer_client;

    @Override
    public void start(Stage stage) throws Exception {
        customer_client = new Customer_Client();
        customer_client.start(stage);

    }
    @Test
    public void checkOfferArrival(){
        TextField when = (TextField) lookup("#when").query();
        TextField from= (TextField) lookup("#from").query();
        TextField to = (TextField) lookup("#to").query();
        TextField who = (TextField) lookup("#who").query();
        Button submitButton = (Button) lookup("#submitButton").query();


        clickOn(when).write("12:00");
        //clickOn("#when").write("12:00");
        clickOn(from).write("Wilhelmshöher Allee 73, Kassel");
        clickOn(to).write("DEZ Kassel");
        clickOn(who).write("Carla");
        clickOn(submitButton).clickOn(MouseButton.PRIMARY);
    }

}
