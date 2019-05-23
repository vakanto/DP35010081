package ha05.taxi_client;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class Taxi_client_controller implements Initializable {

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
    private ListView myList;

    private Taxi_Proxy taxi_proxy;


    public Taxi_client_controller() {
        this.taxi_proxy=new Taxi_Proxy("toTransport", "toCharlie", this);
    }

    public void onAcceptButtonClicked(Event event){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = generateAcceptJson();
                System.out.println(jsonObject);
                myList.getItems().add("Send: " + jsonObject.toString());
                myList.refresh();
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

    @FXML
    public synchronized void messageArrived(JSONObject message) {
        System.out.println("message arrived at Taxi-Client");
        System.out.println(myList);
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
                    System.out.println(myList.toString());
                    myList.getItems().add(messageText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
