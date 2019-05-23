package ha05.customer_client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

public class Customer_Client extends Application {

    private final String START_SCREEN = "Customer_Client_Order.fxml";
    private final String OFFER_SCREEN = "Customer_Client_Offer.fxml";

    private Parent root;
    private Stage stage;

    public Customer_Client(){

    }

    @Override
    public void start(Stage stage) throws Exception{
        this.stage=stage;
        root=loadFXML(START_SCREEN);
        this.stage.setTitle("Taxi-Client");
        this.stage.setScene(new Scene(root, 500,500));
        this.stage.setHeight(450);
        this.stage.setWidth(625);
        this.stage.setResizable(false);
        this.stage.show();
        System.out.println(this.stage);
        System.out.println(root.lookup("statusList"));
    }

    private Parent loadFXML(String path) throws Exception{
        Parent parent;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        parent=loader.load();
        return parent;
    }
        public void acceptOffer(){

        }
}
