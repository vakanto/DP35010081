package ha05.customer_client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Offer_Screen extends Application {
    @FXML
    AnchorPane offerPane;

    @FXML
    Button acceptOfferButton;

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
}
