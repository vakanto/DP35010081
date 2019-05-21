package ha05.customer_client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.OffsetDateTime;

public class Offer_Screen extends Application {
    @FXML
    AnchorPane offerPane;

    @FXML
    Button acceptOfferButton;

    @FXML
    Label offerText;

    private static Offer_Screen instance;

    private final String START_SCREEN = "Taxi_Client_Offer.fxml";

    private Stage primaryStage;
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage=primaryStage;
        root=loadFXML(START_SCREEN);
        this.primaryStage.setTitle("Taxi-Client");
        this.primaryStage.setScene(new Scene(root, 800,500));
        this.primaryStage.show();
    }

    public AnchorPane getPane(){
        return this.offerPane;
    }
    private Parent loadFXML(String path) throws Exception{
        Parent parent;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        parent=loader.load();
        return parent;
    }

    @FXML
    public void messageArrived(JSONObject message){
        System.out.println("message arrived");
        String messageText=null;
        try {
            String name = message.getString("name");
            String price = message.getString("price");
            String time = message.getString("time");
            messageText="Name: " + name + "\n" + "Preis: " + price + "Zeitpunkt: " + time;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        offerText.setText(messageText);

    }
}
