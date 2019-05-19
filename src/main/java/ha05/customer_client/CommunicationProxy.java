package ha05.customer_client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class CommunicationProxy {
    private MqttClient client;


    public CommunicationProxy(){
        try {
            client =  new MqttClient("tcp://127.0.0.1:2000", MqttClient.generateClientId());
        } catch (MqttException e) {
            e.printStackTrace();
        }
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
}
