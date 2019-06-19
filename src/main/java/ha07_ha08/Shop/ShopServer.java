package ha07_ha08.Shop;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.fulib.yaml.Yamler;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
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

        //LinkedHashMap <String,String> event = new ObjectMapper().readValue(message.toString(), LinkedHashMap.class);
        ArrayList<LinkedHashMap <String,String>> events = new Yamler().decodeList(message.toString());
        shopBuilder.applyEvents(events);

        String response = "Ok" + exchange.getRequestURI();
        exchange.sendResponseHeaders(200,response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
