package ha05.customer_client;

import ha05.taxi_client.Taxi_Client;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.testfx.framework.junit.ApplicationTest;


public class test_ha05 extends ApplicationTest {




    private Customer_Client customer_client;
    private Taxi_Client taxi_client;
    private Stage client;
    private Stage taxi;

    @Override
    public void start(Stage stage) throws Exception {
        client=stage;
        taxi=new Stage();
        customer_client = new Customer_Client();
        customer_client.start(stage);
        taxi_client = new Taxi_Client();
        taxi_client.start(taxi);
    }
    @Test
    public void checkOfferArrival() throws IOException {
        Process p = Runtime.getRuntime().exec("mosquitto -c " + "src/main/resources/mosquitto.conf");
        CommunicationProxy proxy = new CommunicationProxy("toTransport", "toCharlie", null);
        TextField when = (TextField) lookup("#when").query();
        TextField from= (TextField) lookup("#from").query();
        TextField to = (TextField) lookup("#to").query();
        TextField who = (TextField) lookup("#who").query();
        Button submitButton = (Button) lookup("#submitButton").query();


        clickOn(when).write("12:00");
        clickOn(from).write("Wilhelmshöher Allee 73, Kassel");
        clickOn(to).write("DEZ Kassel");
        clickOn(who).write("Carla");
        clickOn(submitButton).clickOn(MouseButton.PRIMARY);

        JSONObject message = new JSONObject();
        try {
            message.put("name", "Thea");
            message.put("price", "12 Euro");
            message.put("time", "12:07 Uhr");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    proxy.sendMessage(message);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClients() throws IOException {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                taxi.toBack();
            }
        });
        Process p = Runtime.getRuntime().exec("mosquitto -c " + "src/main/resources/mosquitto.conf");
        TextField when = (TextField) lookup("#when").query();
        TextField from= (TextField) lookup("#from").query();
        TextField to = (TextField) lookup("#to").query();
        TextField who = (TextField) lookup("#who").query();
        Button submitButton = (Button) lookup("#submitButton").query();


        TextField howMuch = (TextField) lookup("#howMuch").query();
        TextField acceptTime = (TextField) lookup("#offerAcceptTime").query();
        Button acceptButton = (Button) lookup("#acceptButton").query();


        clickOn(when).write("12:00");
        clickOn(from).write("Wilhelmshöher Allee 73, Kassel");
        clickOn(to).write("DEZ Kassel");
        clickOn(who).write("Carla");
        clickOn(submitButton).clickOn(MouseButton.PRIMARY);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                client.toBack();
                taxi.toFront();
            }
        });
        clickOn(howMuch).write("12");
        clickOn(acceptTime).write("12:07 Uhr");
        clickOn(acceptButton).clickOn(MouseButton.PRIMARY);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                taxi.toBack();
                client.toFront();
            }
        });
    }
}


