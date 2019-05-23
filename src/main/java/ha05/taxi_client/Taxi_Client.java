package ha05.taxi_client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;


public class Taxi_Client extends Application {

    private Parent root;
    private final String START_SCREEN = "Taxi_Client.fxml";
    private Taxi_client_controller taxi_client_controller;
    private Taxi_client_model taxi_client_model;
    private  Taxi_client_view taxi_client_view;
    private Stage stage;
    private Taxi_Proxy taxi_proxy;

    public Taxi_Client(){


    }

    @Override
    public void start(Stage stage) throws Exception{
        taxi_client_model = new Taxi_client_model();
        taxi_client_view = new Taxi_client_view(taxi_client_model, stage);
        this.stage=stage;
        root=loadFXML(START_SCREEN);
        this.stage.setTitle("Taxi-Client");
        this.stage.setScene(new Scene(root, 500,500));
        this.stage.setHeight(450);
        this.stage.setWidth(625);
        this.stage.setResizable(false);
        this.stage.show();
        System.out.println(this.stage);
    }

    private Parent loadFXML(String path) throws Exception{
        Parent parent;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        parent=loader.load();
        return parent;
    }
}
