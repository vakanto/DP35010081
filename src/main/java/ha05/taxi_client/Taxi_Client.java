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
    TextField howMuch;

    @FXML
    TextField offerAcceptTime;

    @FXML
    TextField pickUpTime;

    @FXML
    TextField dropTime;

    @FXML
    Button acceptButton;

    @FXML
    Button dropButton;

    @FXML
    Button pickUpButton;

    @FXML
    ListView statusList;
    private Stage stage;
    private Parent root;

    private final String START_SCREEN = "Taxi_Client.fxml";

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

    private Parent loadFXML(String path) throws Exception{
        Parent parent;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        parent=loader.load();
        return parent;
    }


    public void messageArrived(JSONObject message) {
        System.out.println("message arrived");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String messageText=null;
                    String who = message.getString("who");
                    String when = message.getString("when");
                    String from = message.getString("from");
                    String to = message.getString("to");
                    messageText="Wer: " + who + "\n" + "Wann: " + when + "\n" + "Von: " + from + "Nach: " + to;
                    System.out.println(messageText);
                    statusList.getItems().add(messageText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onAcceptButtonClicked(Event event){
        Taxi_Proxy proxy = new Taxi_Proxy(null,null, this);
        JSONObject jsonObject = generateAcceptJson();
        System.out.println(jsonObject);
        statusList.getItems().add("Send: " + jsonObject.toString());
        statusList.refresh();
        proxy.sendMessage(jsonObject);
    }

    @FXML
    private JSONObject generateAcceptJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("when", offerAcceptTime.getText());
            object.put("costs", howMuch.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
