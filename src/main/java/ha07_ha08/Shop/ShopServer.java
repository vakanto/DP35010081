package ha07_ha08.Shop;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.fulib.yaml.Yamler;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ShopServer {

    public static ShopBuilder shopBuilder;
    private static WarehouseProxy warehouseProxy;
    //private static Executor executor;
    public ShopServer(){
        shopBuilder = new ShopBuilder();
    }
    private static Date time;
    private static String lastKnownWarhouseEventTime;

    public static void main(String args[]){
        HttpServer server = null;

        try{
            server=HttpServer.create(new InetSocketAddress(5001 ),0);
            //executor = Executors.newSingleThreadExecutor();
            //server.setExecutor(executor);
            HttpContext context = server.createContext("/postEvent");
            context.setHandler(x->handlePostEvent(x));

            HttpContext warhouseEventContext = server.createContext("/getWarhouseEvents");
            //context.setHandler(x->shopBuilder.getWarehouseProxy().getWarehouseEvents());

            server.start();

        }catch (Exception e){
            System.out.println("Server failed during Startup");
        }

    }

    /**private static void retrieveNewEventsFromWarehouse() {
        String warehouseEvents = sendRequest(GET_SHOP_EVENTS_URL, "lastKnown" + lastKnownWarhouseEventTime);
        ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(warehouseEvents);
        executor.execute(()-> shopBuilder.applyEvents(events));
    }**/

    private static void handlePostEvent(HttpExchange exchange) throws IOException {
        String body = getBody(exchange);

        //LinkedHashMap <String,String> event = new ObjectMapper().readValue(message.toString(), LinkedHashMap.class);
        ArrayList<LinkedHashMap <String,String>> events = new Yamler().decodeList(body.toString());
        shopBuilder.applyEvents(events);
        lastKnownWarhouseEventTime=time.toString();
        warehouseProxy.getWarehouseEvents(lastKnownWarhouseEventTime);
        writeAnswer(exchange, "Ok" + exchange.getRequestURI());
        return;
    }

    public static String getBody(HttpExchange exchange) throws IOException {
        URI requestURI = exchange.getRequestURI();
        InputStream requestBody = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody, StandardCharsets.UTF_8));
        StringBuilder text = new StringBuilder();

        while(true){
            String line = reader.readLine();
            if(line==null){
                break;
            }
            text.append(line);
        }
        String yaml = text.toString();
        return yaml;
    }

    private static void writeAnswer(HttpExchange exchange, String response){
        try {
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200,bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        } catch (IOException e) {
            Logger.getGlobal().info("Failed during response.");
        }
    }
}