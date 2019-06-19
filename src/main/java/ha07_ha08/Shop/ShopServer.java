package ha07_ha08.Shop;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.fulib.yaml.Yamler;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ShopServer {
    public static ShopBuilder shopBuilder;

    private static Executor executor;
    public ShopServer(){
        shopBuilder = new ShopBuilder();
    }

    public static void main(String args[]){
        HttpServer server = null;

        try{
            server=HttpServer.create(new InetSocketAddress(5001 ),0);
            executor = Executors.newSingleThreadExecutor();
            server.setExecutor(executor);
            HttpContext context = server.createContext("/postEvent");
            context.setHandler(x->handlePostEvent(x));

            HttpContext warhouseEventContext = server.createContext("/getWarhouseEvents");
            context.setHandler(x->shopBuilder.getTheWarehouse().getWarehouseEvents(x));

            server.start();

            retrieveNewEventsFromWarehouse();

        }catch (Exception e){
            System.out.println("Server failed during Startup");
        }

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

    private static void retrieveNewEventsFromWarehouse() {
        String warehouseEvents = sendRequest(http://127.0.0.1:5002/getShopEvents, );
        ArrayList<LinkedHashMap<String,String>> events = new Yamler().decodeList(warehouseEvents);
        executor.execute(()-> shopBuilder.applyEvents(events));
    }

    private static void handlePostEvent(HttpExchange exchange) throws IOException {
        String body = getBody(exchange);

        //LinkedHashMap <String,String> event = new ObjectMapper().readValue(message.toString(), LinkedHashMap.class);
        ArrayList<LinkedHashMap <String,String>> events = new Yamler().decodeList(message.toString());
        shopBuilder.applyEvents(events);



        retrieveNewEventsFromWarehouse();

        return;
    }

    private static void writeAnswer(HttpExchange exchange,String response){
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
