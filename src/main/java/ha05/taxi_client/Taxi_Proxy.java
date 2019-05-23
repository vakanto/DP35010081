package ha05.taxi_client;

import javafx.application.Platform;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class Taxi_Proxy implements MqttCallback {

    private  String SENDING_CHANNEL = "toCharlie";
    private  String RECEIVE_CHANNEL= "toTransport";
    private MqttClient client;
    private LinkedList<JSONObject> messages;
    private Taxi_client_controller taxi_client_controller;


    public Taxi_Proxy(String receiveChannel, String sendingChannel, Taxi_client_controller taxi_client_controller){
        try {
            this.taxi_client_controller=taxi_client_controller;
            client =  new MqttClient("tcp://127.0.0.1:1883", MqttClient.generateClientId());
            client.setCallback(this);
            client.connect();
            client.subscribe(RECEIVE_CHANNEL);
            System.out.println("Taxi Client is connected: " + client.isConnected());
        } catch (MqttException e) {
            e.printStackTrace();
        }
        messages=new LinkedList<>();

        if(receiveChannel != null){
            RECEIVE_CHANNEL=receiveChannel;
        }
        if(sendingChannel != null){
            SENDING_CHANNEL = sendingChannel;
        }

        System.out.println("Taxi Proxy sends on: " + SENDING_CHANNEL);
        System.out.println("Taxi Proxy receives on: " + RECEIVE_CHANNEL);
    }
    public boolean sendMessage(JSONObject object) {
        MqttMessage message = new MqttMessage();
        message.setPayload(object.toString().getBytes());
        try {
            if(client.isConnected()==false){
                client.connect();
            }
            client.publish(SENDING_CHANNEL, message);
            System.out.println("Proxy sends on " + SENDING_CHANNEL + message);
            //client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(cause.toString());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("HUHU" + message.toString());
                System.out.println("Message arrived at Taxi Proxy.");
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(message.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("JSOn OBJECT" + jsonObject);
                messages.add(jsonObject);
                System.out.println(messages.toString());
                taxi_client_controller.messageArrived(jsonObject);
            }
        });
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    public JSONObject getLastMessage(){
        if(!messages.isEmpty()) {
            return messages.pop();
        }
        else{
            return null;
        }
    }
}
