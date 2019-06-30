package ha07_ha08.Shop;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.fulib.yaml.EventSource;
import org.fulib.yaml.Yamler;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

public class ShopServer {

    public static ShopBuilder shopBuilder;
    public static HttpServer server;
    public ShopServer(){

    }

    public static void main(String args[]) throws IOException {
        try{
            server=HttpServer.create(new InetSocketAddress(5001 ),0);
            HttpContext context = server.createContext("/postEvent");
            context.setHandler(x->handlePostEvent(x));
            server.start();
            System.out.println("Server started.");

        }catch (Exception e){
            System.out.println("Server failed during Startup");
            System.out.println(e);

         }
        shopBuilder = new ShopBuilder();
        System.out.println("Created ShopBuilder.");
    }

    private static void handlePostEvent(HttpExchange exchange) throws IOException {
        System.out.println("Shop Server received event.");
        String body = getBody(exchange);
        ArrayList<LinkedHashMap <String,String>> events = new Yamler().decodeList(body.toString());
        String response = shopBuilder.applyEvents(events, 0);
        if(!response.isEmpty()){
            writeAnswer(exchange, response);
            return;
        }
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
