package ha07_ha08.Shop;

import com.mashape.unirest.http.exceptions.UnirestException;
import ha07_ha08.Shop.Model.Shop;
import ha07_ha08.Shop.Model.ShopProduct;
import ha07_ha08.Warehouse.Model.Warehouse;
import ha07_ha08.Warehouse.Model.WarehouseProduct;
import org.fulib.yaml.EventSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ShopBuilder {

    private EventSource eventSource;
    public Shop shop;
    private WarehouseProxy warehouseProxy;

    public ShopBuilder(){
        eventSource=new EventSource();
        shop=new Shop()
            .setName("DerLaden");
        warehouseProxy=new WarehouseProxy(this);
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
        eventSource.append(event);

        try {
            warehouseProxy.orderProduct(event);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }


    public void applyEvents(ArrayList<LinkedHashMap<String, String>> events) {
        for(LinkedHashMap<String,String>event : events){
        if("add_product_to_shop".equals(event.get("event_type"))){
            String numberOfItems= event.get("size");
            int itemCount = Integer.parseInt(numberOfItems);
            addProductToShop(event.get("event_key"), event.get("product_name"), itemCount);
            }
        }
    }

    public void addProductToShop(String eventKey, String product_name, int itemCount){
        LinkedHashMap<String, String> event = eventSource.getEvent(eventKey);

        if(event!=null){
            //event allready exists
            return;
        }

        ShopProduct shopProduct = getFromProducts(product_name);
        double inStock = shopProduct.getInStock();
        shopProduct.setInStock(inStock+itemCount);

        shop.withProducts(shopProduct);

        event = new LinkedHashMap<>();
        event.put("event_type","add_product_to_shop");
        event.put("event_key", eventKey);
        event.put("product_name", product_name);
        event.put("itemCount","" + shopProduct.getInStock());
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
