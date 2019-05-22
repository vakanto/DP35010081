package ha05.taxi_client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;


public class Taxi_Client extends Application {

    @FXML
    private TextField howMuch;

    @FXML
    private TextField offerAcceptTime;

    @FXML
    private TextField pickUpTime;

    @FXML
    private TextField dropTime;

    @FXML
    private Button acceptButton;

    @FXML
    private Button dropButton;

    @FXML
    private Button pickUpButton;

    @FXML
    private ListView eventList;

    private Stage stage;
    private Parent root;
    private Taxi_Proxy taxi_proxy;
    private final String START_SCREEN = "Taxi_Client.fxml";

    @Override
    public void start(Stage stage) throws Exception{
        System.out.println(offerAcceptTime);
        taxi_proxy = new Taxi_Proxy(null,null,this);
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

    @FXML
    public void messageArrived(JSONObject message) {
        System.out.println("message arrived at Taxi-Client");
        System.out.println(eventList);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String messageText=null;
                    String who = message.getString("Who");
                    String when = message.getString("When");
                    String from = message.getString("From");
                    String to = message.getString("To");
                    messageText="Wer: " + who + "\n" + "Wann: " + when + "\n" + "Von: " + from + "Nach: " + to;
                    System.out.println(messageText);
                    System.out.println(eventList);
                    eventList.getItems().add(messageText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onAcceptButtonClicked(Event event){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = generateAcceptJson();
                System.out.println(jsonObject);
                eventList.getItems().add("Send: " + jsonObject.toString());
                eventList.refresh();
                taxi_proxy.sendMessage(jsonObject);
            }
        });
    }

    @FXML
    private JSONObject generateAcceptJson() {
        System.out.println("Generate Answer");
        JSONObject object = new JSONObject();
        try {
            object.put("name", "Thea");
            object.put("when", offerAcceptTime.getText());
            System.out.println(offerAcceptTime.getText());
            object.put("costs", howMuch.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object.toString());
        return object;
    }
}
