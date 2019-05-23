package ha05;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import io.moquette.broker.Server;
import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttMessageBuilders;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;
import org.apache.log4j.BasicConfigurator;

import java.util.List;
import static java.nio.charset.StandardCharsets.UTF_8;

public final class EmbeddedLauncher {
    static class PublisherListener extends AbstractInterceptHandler {

        @Override
        public String getID() {
            return "EmbeddedLauncherPublishListener";
        }

        @Override
        public void onPublish(InterceptPublishMessage msg) {
            final String decodedPayload = new String(msg.getPayload().array(), UTF_8);
            System.out.println("Received on topic: " + msg.getTopicName() + " content: " + decodedPayload);
        }
    }

    public void startServer(){
        BasicConfigurator.configure();
        Server mqttBroker = new Server();

        IResourceLoader resourceLoader = new ClasspathResourceLoader();
        IConfig config = new ResourceLoaderConfig(resourceLoader);

        List<? extends InterceptHandler> handlerList = Collections.singletonList(new PublisherListener());

        try {
            System.out.println("Starte Server");
            mqttBroker.startServer(config, handlerList);
        } catch (IOException e) {
            System.out.println("Starten des Servers ist fehlgeschlagen");
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Server shutdown..");
            mqttBroker.stopServer();
            System.out.println("Server has been shutdown");
        }));
    }



}
