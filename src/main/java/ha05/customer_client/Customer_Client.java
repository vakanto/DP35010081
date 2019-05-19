package ha05.customer_client;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

public class Customer_Client extends Application {

    private final String START_SCREEN = "Taxi_Client_Order.fxml";
    private final String OFFER_SCREEN = "Taxi_Client_Offer.fxml";

    private Parent root;
    private Stage stage;

    @FXML
    private TextField Who;
    @FXML
    private TextField From;
    @FXML
    private TextField When;
    @FXML
    private TextField To;

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
    }

    public void submitButtonPressed(Event event) throws Exception{
        CommunicationProxy proxy = new CommunicationProxy();
        JSONObject jsonObject = generateJson();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        root=loadFXML(OFFER_SCREEN);
        stage.setScene( new Scene(root,800, 500));
        System.out.println(jsonObject);
        stage.show();
        proxy.sendMessage(jsonObject);
    }
    @FXML
    private JSONObject generateJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("Who",  Who.getText());
            object.put("To", To.getText());
            object.put("From", From.getText());
            object.put("When", When.getText());
            object.put("Receiver", "toTransporter");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    private Parent loadFXML(String path) throws Exception{
        Parent parent;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        parent=loader.load();
        return parent;
    }
}
