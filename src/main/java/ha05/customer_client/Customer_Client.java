package ha05.customer_client;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    private Parent root;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception{
        this.stage=stage;
        root=loadFXML(START_SCREEN);
        this.stage.setTitle("Taxi-Client");
        this.stage.setScene(new Scene(root, 800,500));
        this.stage.show();
        System.out.println(this.stage);
    }

    public void submitButtonPressed(Event event) throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        root=loadFXML(OFFER_SCREEN);
        stage.setScene( new Scene(root,800, 500));
        stage.show();
    }

    private Parent loadFXML(String path) throws Exception{
        Parent parent;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        parent=loader.load();
        return parent;
    }
}
