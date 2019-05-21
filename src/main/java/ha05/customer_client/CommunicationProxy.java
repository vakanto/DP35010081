package ha05.customer_client;

import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class CommunicationProxy implements MqttCallback {
    private MqttClient client;
    private LinkedList<JSONObject> messages;
    private Customer_Client customer_client;
    private Offer_Screen offer_screen;


    public CommunicationProxy(){
        try {
            client =  new MqttClient("tcp://127.0.0.1:2000", MqttClient.generateClientId());
        } catch (MqttException e) {
            e.printStackTrace();
        }
        messages=new LinkedList<>();
        offer_screen=new Offer_Screen();
    }

    public boolean sendMessage(JSONObject object) {
        MqttMessage message = new MqttMessage();
        message.setPayload(object.toString().getBytes());
        try {
            client.connect();
            client.publish(object.getString("Receiver"), message);
            client.disconnect();
        } catch (MqttException | JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void receiveMessage(){

    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        JSONObject jsonObject = new JSONObject(message.toString());
        messages.add(jsonObject);
        offer_screen.messageArrived(jsonObject);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public JSONObject getLastMessage(){
        return messages.pop();
    }
}
