package ha05.customer_client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttCallBack implements MqttCallback {

    private String lastMessage;

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost!");
    }

    public String getLastMessage() {
        return lastMessage;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        lastMessage=message.toString();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
