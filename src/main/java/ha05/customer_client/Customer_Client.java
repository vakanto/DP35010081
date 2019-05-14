package ha05.customer_client;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.*;

import java.io.File;

public class Customer_Client extends Application {

    private final String START_SCREEN = "Taxi_Client_Order.fxml";
    private final String OFFER_SCREEN = "Taxi_Client_Offer.fxml";

    @FXML
    private Button submitOrder;

    private Parent root;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{


        this.primaryStage=primaryStage;
        root=loadFXML(START_SCREEN);
        this.primaryStage.setTitle("Taxi-Client");
        this.primaryStage.setScene(new Scene(root, 800,500));

        /**submitOrder.setOnAction(e -> {
            Offer_Screen offer_screen = new Offer_Screen();
            primaryStage.getScene().setRoot(offer_screen.getPane());
        });**/
        this.primaryStage.show();
    }

    public void submitButtonPressed(Event event) throws Exception{
        Button submit = (Button) event.getSource();
        //Parent parent = loadFXML(OFFER_SCREEN);
        Offer_Screen offer_screen = new Offer_Screen();
        primaryStage.setScene(new Scene( offer_screen.getPane(),800, 500));
        primaryStage.show();
    }

    private Parent loadFXML(String path) throws Exception{
        Parent parent;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        parent=loader.load();
        return parent;
    }
}
