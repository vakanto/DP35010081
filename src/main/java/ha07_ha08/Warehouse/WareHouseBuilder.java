package ha07_ha08.Warehouse;

import com.mashape.unirest.http.exceptions.UnirestException;
import ha07_ha08.Shop.ShopBuilder;
import ha07_ha08.Warehouse.Model.*;
import org.fulib.yaml.EventFiler;
import org.fulib.yaml.EventSource;
import org.fulib.yaml.Yamler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class WareHouseBuilder {
    private Warehouse warehouse;
    private ShopProxy shopProxy;
    private EventSource eventSource;
    private EventFiler eventFiler;

    public WareHouseBuilder() throws IOException, UnirestException {
        warehouse = new Warehouse();
        shopProxy = new ShopProxy();
        eventSource = new EventSource();
        eventFiler = new EventFiler(eventSource)
            .setHistoryFileName("src/main/java/ha07_ha08/database/Warehouse.yml");

        String history = eventFiler.loadHistory();
        if(history!=null){
            ArrayList<LinkedHashMap<String,String>> eventList = new Yamler().decodeList(history);
            this.applyEvents(eventList);
        }
        eventFiler.startEventLogging();
    }

    public void applyEvents(ArrayList<LinkedHashMap<String, String>> eventList) throws IOException, UnirestException {
        for(LinkedHashMap<String, String> event : eventList){
            if("add_product_to_shop".equals(event.get("event_type"))){
                int size = Integer.valueOf(event.get("size"));
                addLotToStock(event.get("lotID"),event.get("productName"), size);
            }
            else if ("order_product".equals(event.get("event_type"))){
                orderProduct(event.get("event_key"),event.get("productName"), event.get("address"));
            }
        }
    }

    private void orderProduct(String orderID, String productName, String address) {
        WarehouseOrder order = getFromOrders(orderID);

        if(order.getProduct()==null){
            WarehouseProduct product = getFromProducts(productName);
            order.setProduct(product)
                .setAddress(address)
                .setId(orderID);

            LinkedHashMap<String,String>event = new LinkedHashMap<>();
            event.put("event_type","order_product");
            event.put("event_key", orderID);
            event.put("product_name",productName);
            event.put("address", address);
            eventSource.append(event);

            //Reallocate one lot slot.
            Lot lot = product.getLots().get(0);
            double lotSize = lot.getLotSize();
            lot.setLotSize(lotSize-1);
        }
    }

    private WarehouseOrder getFromOrders(String orderID) {
        return new WarehouseOrder();
    }

    public Lot addLotToStock(String lotId, String productName, int size) throws IOException, UnirestException {

        Lot lot = getLot(lotId);
        double oldSize = lot.getLotSize();
        WarehouseProduct warehouseProduct = getFromProducts(productName);

        lot.setWareHouseProduct(warehouseProduct);
        if(!warehouse.getProducts().contains(warehouseProduct)) {
            warehouse.getProducts().add(warehouseProduct);
        }

        if(lot.getPalettePlace()==null){
            for(PalettePlace place : warehouse.getPlaces()){
                if(place.getLot()==null){
                    place.setLot(lot);
                    break;
                }
            }

        }
        lot.setLotSize(size);
        LinkedHashMap<String,String> event = new LinkedHashMap<>();
        event.put("event_type","add_product_to_shop");
        event.put("event_key", lotId);
        event.put("lotID", lotId);
        event.put("productName", productName);
        event.put("size","" + size);
        event.put("old_size", ""+oldSize);
        eventSource.append(event);


        if(oldSize==0.0){
            shopProxy.addProductToShop(event);
        }

        return  lot;
    }

    private WarehouseProduct getFromProducts(String productName) {

        String productID = productName.replaceAll("\\W", "");

        for(WarehouseProduct wp : warehouse.getProducts()){
            if(wp.getName().equals(productName)){
                return wp;
            }
        }
        WarehouseProduct warehouseProduct = new WarehouseProduct()
                .setWarehouse(this.warehouse)
                .setId(productID)
                .setName(productName);
        return warehouseProduct;
    }

    public Lot getLot(String lotId){

        for(WarehouseProduct product : warehouse.getProducts()){
            for(Lot lot : product.getLots()){
                if(lot.getId().equals(lotId)){
                    return lot;
                }
            }
        }
        Lot lot = new Lot()
                .setId(lotId);

        return  lot;
    }

    public double getProductCount(String productName){
        ArrayList<WarehouseProduct> products = warehouse.getProducts();
        for(WarehouseProduct warehouseProduct : products){
            if(productName.equals(warehouseProduct.getName())){
                ArrayList<Lot> lots = warehouseProduct.getLots();
                double count = 0;
                for(Lot lot : lots){
                    count=lot.getLotSize();
                }
                return count;
            }

        }
        return 0;
    }
}
