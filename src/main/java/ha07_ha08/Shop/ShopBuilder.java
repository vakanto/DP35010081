package ha07_ha08.Shop;

import com.mashape.unirest.http.exceptions.UnirestException;
import ha07_ha08.Shop.Model.Shop;
import ha07_ha08.Shop.Model.ShopProduct;
import org.fulib.yaml.EventFiler;
import org.fulib.yaml.EventSource;
import org.fulib.yaml.Yamler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ShopBuilder {

    private EventSource eventSource;
    public Shop shop;
    private WarehouseProxy warehouseProxy;
    private EventFiler eventFiler;

    public ShopBuilder(){
        eventSource=new EventSource();
        shop=new Shop()
            .setName("DerLaden");
        warehouseProxy=new WarehouseProxy(this);

        eventFiler = new EventFiler(eventSource)
                .setHistoryFileName("src/main/java/ha07_ha08/database/Shop.yml");

        String history = eventFiler.loadHistory();
        if(history!=null){
            ArrayList<LinkedHashMap<String,String>> eventList = new Yamler().decodeList(history);
            this.applyEvents(eventList);
        }
        eventFiler.startEventLogging();
    }

    public void orderProduct(String product_name, String address, String orderID){
        LinkedHashMap<String, String> event = new LinkedHashMap<>();
        ShopProduct product = getFromProducts(product_name);
        double oldCount=product.getInStock();
        product.setInStock(oldCount-1);
        event.put("event_type","order_product");
        event.put("event_key", orderID);
        event.put("product_name", product_name);
        event.put("address", address);
        event.put("orderID", orderID);
        eventSource.append(event);

        try {
            warehouseProxy.orderProduct(event);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }


    public String applyEvents(ArrayList<LinkedHashMap<String, String>> events) {
        for(LinkedHashMap<String,String>event : events){
        if("add_product_to_shop".equals(event.get("event_type"))){
            String numberOfItems = event.get("itemCount");
            double itemCount = Double.parseDouble(numberOfItems);
            addProductToShop(event.get("event_key"), event.get("product_name"), itemCount);
            }
        else if("delete_shop".equals(event.get("event_type"))){
            for(ShopProduct product : shop.getProducts()){
                product=null;
            }
            shop=new Shop();
            String history = eventFiler.loadHistory();
            File file = new File("src/main/java/ha07_ha08/database/Shop.yml");
            ArrayList<LinkedHashMap<String,String>> eventList = new Yamler().decodeList(history);
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.applyEvents(eventList);
            }
        else if("order_product".equals(event.get("event_type"))){
            orderProduct(event.get("product_name"), event.get("address"), event.get("orderID"));
        }
        else if("getEvents".equals(event.get("event_type"))){
            return sendEvents(event);
        }
        }
        return null;
    }

    private String sendEvents(LinkedHashMap<String, String> event) {
        String history = eventFiler.loadHistory();
        //warehouseProxy.sendEvents(new Yamler().decodeList(history));
        return EventSource.encodeYaml(new Yamler().decodeList(history));
    }

    public void addProductToShop(String eventKey, String product_name, double itemCount){
        LinkedHashMap<String, String> event = eventSource.getEvent(eventKey);

        if(event!=null){
            //event allready exists
            return;
        }

        ShopProduct shopProduct = getFromProducts(product_name);
        double inStock = shopProduct.getInStock();

        shopProduct.setInStock(itemCount+inStock);
        shop.withProducts(shopProduct);

        event = new LinkedHashMap<>();
        event.put("event_type","add_product_to_shop");
        event.put("event_key", eventKey);
        event.put("product_name", product_name);
        event.put("itemCount","" + itemCount);
        eventSource.append(event);
    }

    public ShopProduct getFromProducts(String productName) {
        String productId = productName.replaceAll("\\W","");
        for(ShopProduct product : shop.getProducts()){
            if(product.getName().equals(productName)){
                return product;
            }
        }
        ShopProduct shopProduct = new ShopProduct()
                .setName(productName)
                .setId(productId)
                .setShop(this.shop);
        return shopProduct;
    }

    public WarehouseProxy getWarehouseProxy() {
        return warehouseProxy;
    }
}
