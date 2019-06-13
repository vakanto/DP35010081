package ha07.Shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;

public class ShopServer {
    public static ShopBuilder shopBuilder;

    public ShopServer(){
        shopBuilder = new ShopBuilder();
    }

    public static void main(String args[]){
        HttpServer server = null;

        try{
            server=HttpServer.create(new InetSocketAddress(5001 ),0);
            HttpContext context = server.createContext("/postEvent");
            context.setHandler(x->handlePostEvent(x));

            server.start();

        }catch (Exception e){
            System.out.println("Server failed during Startup");
        }

    }

    private static void handlePostEvent(HttpExchange exchange) throws IOException {
        InputStream inputStreamReader = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamReader));

        StringBuilder message = new StringBuilder();

        while(true){
            String line = reader.readLine();
            if(line==null){
                break;
            }
            message.append(line).append("\n");
        }

        LinkedHashMap <String,String> event = new ObjectMapper().readValue(message.toString(), LinkedHashMap.class);
        shopBuilder.applyEvents(event);
    }
}
