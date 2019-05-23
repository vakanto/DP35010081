package ha05.customer_client;

import ha05.taxi_client.Taxi_Client;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
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
        ListView statusList = (ListView) lookup("#statusList").query();


        TextField howMuch = (TextField) lookup("#howMuch").query();
        TextField acceptTime = (TextField) lookup("#offerAcceptTime").query();
        Button acceptButton = (Button) lookup("#acceptButton").query();
        TextField pickUpTime = (TextField) lookup("#pickUpTime").query();
        Button pickUpButton = (Button) lookup("#pickUpButton").query();
        Button dropButton = (Button) lookup("#dropButton").query();
        TextField dropTime = (TextField) lookup("#dropTime").query();

        clickOn(when).write("12:00");
        clickOn(from).write("Wilhelmshöher Allee 73, Kassel");
        clickOn(to).write("DEZ Kassel");
        clickOn(who).write("Carla");
        Assert.assertEquals("12:00",when.getText());
        Assert.assertEquals("Wilhelmshöher Allee 73, Kassel",from.getText());
        Assert.assertEquals("DEZ Kassel",to.getText());
        Assert.assertEquals("Carla",who.getText());
        clickOn(submitButton).clickOn(MouseButton.PRIMARY);
        sleep(2000);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                client.toBack();
                taxi.toFront();
            }
        });
        clickOn(howMuch).write("12");
        clickOn(acceptTime).write("12:07 Uhr");

        Assert.assertEquals("12",howMuch.getText());
        Assert.assertEquals( "12:07 Uhr", acceptTime.getText());

        clickOn(acceptButton).clickOn(MouseButton.PRIMARY);
        sleep(2000);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                taxi.toBack();
                client.toFront();
            }
        });

        sleep(2000);

        clickOn(acceptButton).clickOn(MouseButton.PRIMARY);

        sleep(2000);


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                client.toBack();
                taxi.toFront();
            }
        });

        clickOn(pickUpTime).write("12:11");
        clickOn(pickUpButton).clickOn(MouseButton.PRIMARY);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                taxi.toBack();
                client.toFront();
            }
        });

        sleep(2000);

        clickOn(acceptButton).clickOn(MouseButton.PRIMARY);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                client.toBack();
                taxi.toFront();
            }
        });
        clickOn(dropTime).write("12:42");
        clickOn(dropButton).clickOn(MouseButton.PRIMARY);

        sleep(2000);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                taxi.toBack();
                client.toFront();
            }
        });
        clickOn(statusList).scroll(30);
        clickOn(acceptButton).clickOn(MouseButton.PRIMARY);
        sleep(2000);
    }
}


