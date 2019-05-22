package ha05.taxi_client;

import ha05.taxi_client.Taxi_Client;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;

import java.util.LinkedList;

public class Taxi_Proxy implements MqttCallback {

    private  String SENDING_CHANNEL = "toCharlie";
    private  String RECEIVE_CHANNEL= "toTransport";
    private MqttClient client;
    private LinkedList<JSONObject> messages;
    private Taxi_Client taxi_client;


    public Taxi_Proxy(String receiveChannel, String sendingChannel, Taxi_Client taxi_client){
        try {
            this.taxi_client=taxi_client;
            client =  new MqttClient("tcp://127.0.0.1:2000", MqttClient.generateClientId());
            client.setCallback(this);
            client.connect();
            client.subscribe(RECEIVE_CHANNEL);
            System.out.println("Client is connected: " + client.isConnected());
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
        System.out.println("HUHU" + message.toString());
        System.out.println("Message arrived at Taxi Proxy.");
        JSONObject jsonObject = new JSONObject(message.toString());
        System.out.println("JSOn OBJECT" + jsonObject);
        messages.add(jsonObject);
        System.out.println(messages.toString());
        System.out.println(taxi_client.toString());
        taxi_client.messageArrived(jsonObject);
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
