package ha07.Shop;

import ha07.Shop.Model.Shop;
import ha07.Shop.Model.ShopProduct;
import org.fulib.yaml.EventSource;

import java.util.LinkedHashMap;

public class ShopBuilder {

    private EventSource eventSource;
    private Shop shop;

    public ShopBuilder(){
        eventSource=new EventSource();
        shop=new Shop();
    }

    public void applyEvents(LinkedHashMap<String, String> event) {
        if("add_product_to_shop".equals(event.get("event_type"))){
            String numberOfItems= event.get("size");
            int itemCount = Integer.parseInt(numberOfItems);
            addProductToShop(event.get("event_key"), event.get("productName"), itemCount);
        }
    }

    public void addProductToShop(String eventKey, String productName, int itemCount){
        LinkedHashMap<String, String> event = eventSource.getEvent(eventKey);

        if(event!=null){
            //event allready exists
            return;
        }

        ShopProduct shopProduct = getFromProducts(productName);
        double inStock = shopProduct.getInStock();
        shopProduct.setInStock(inStock+itemCount);

        event = new LinkedHashMap<>();
        event.put("event_type","add_product_to_shop");
        event.put("event_key", eventKey);
        event.put("productName", productName);
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
}
