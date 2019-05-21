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
    private static Customer_Client instance;

    private Parent root;
    private Stage stage;

    @FXML
    private ListView statusList;

    @FXML
    private TextField who;
    @FXML
    private TextField from;
    @FXML
    private TextField when;
    @FXML
    private TextField to;

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
        CommunicationProxy proxy = new CommunicationProxy(null,null, this);
        JSONObject jsonObject = generateJson();
        Node node = (Node) event.getSource();
        //Stage stage = (Stage) node.getScene().getWindow();
        //root=loadFXML(OFFER_SCREEN);
        //stage.setScene( new Scene(root,800, 500));
        System.out.println(jsonObject);
        //stage.show();
        statusList.getItems().add("Send: " + jsonObject.toString());
        statusList.refresh();
        proxy.sendMessage(jsonObject);
    }
    @FXML
    private JSONObject generateJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("Who",  who.getText());
            object.put("To", to.getText());
            object.put("From", from.getText());
            object.put("When", when.getText());
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

    @FXML
    public void messageArrived(JSONObject message){
        System.out.println("message arrived");
            Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String messageText=null;
                    String name = message.getString("name");
                    String price = message.getString("price");
                    String time = message.getString("time");
                    messageText="Name: " + name + "\n" + "Preis: " + price + "\n" + "Zeitpunkt: " + time;
                    System.out.println(messageText);
                    statusList.getItems().add(messageText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            });
        }
        public void acceptOffer(){

        }
}
