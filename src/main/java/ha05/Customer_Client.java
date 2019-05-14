package ha05;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.*;

import java.io.File;

public class Customer_Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root;
        File file = new File("src/main/resources/ha05/Taxi_Client_Order.fxml");
        System.out.println(file.exists());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Taxi_Client_Order.fxml"));
        System.out.println(loader.getLocation());
        root=loader.load();
        primaryStage.setTitle("Taxi-Client");
        primaryStage.setScene(new Scene(root, 800,500));
        primaryStage.show();
    }


    public void submitButtonPressed(Event event){
        Button submit = (Button) event.getSource();


    }
}
