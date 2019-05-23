package ha05.customer_client;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.json.JSONException;
import org.json.JSONObject;

public class Customer_Client_Controller {

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


    private CommunicationProxy communicationProxy;

    public Customer_Client_Controller(){
        this.communicationProxy=new CommunicationProxy(null,null,this);
    }

    public synchronized void submitButtonPressed(Event event) throws Exception{

        System.out.println(communicationProxy);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = generateJson();
                statusList.getItems().add("Send: " + jsonObject.toString());
                statusList.refresh();
                System.out.println(communicationProxy);
                communicationProxy.sendMessage(jsonObject);
            }
        });
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
                    String price = message.getString("costs");
                    String time = message.getString("when");
                    messageText="Name: " + name + "\n" + "Preis: " + price + "\n" + "Zeitpunkt: " + time;
                    System.out.println(messageText);
                    statusList.getItems().add(messageText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private JSONObject generateJson() {
        JSONObject object = new JSONObject();
        System.out.println(who.getText());
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

}
