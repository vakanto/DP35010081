package ha07_ha08.Warehouse;

import ha07_ha08.Warehouse.Model.*;
import org.fulib.yaml.EventFiler;
import org.fulib.yaml.EventSource;
import org.fulib.yaml.Yamler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.SortedMap;


public class WareHouseBuilder {

    public Warehouse warehouse;
    private ShopProxy shopProxy;
    private EventSource eventSource;
    private EventFiler eventFiler;

    public WareHouseBuilder() throws IOException{
        warehouse = new Warehouse()
            .setName("DasWarenhaus");
        shopProxy = new ShopProxy();
        eventSource = new EventSource();
        eventFiler = new EventFiler(eventSource)
            .setHistoryFileName("/server/Warehouse.yml");

        String history = eventFiler.loadHistory();
        ArrayList<LinkedHashMap<String,String>> eventList=null;

        if(history!=null){

            ArrayList<LinkedHashMap<String,String>> warehouseEventList = new Yamler().decodeList(history);
            for (LinkedHashMap<String,String> event:warehouseEventList) {
                eventSource.append(event);
            }
            eventFiler.storeHistory();
            File file = new File("/server/Warehouse.yml");
            eventList = new Yamler().decodeList(shopProxy.loadEvents(eventSource.getLastEventTime()));
            this.applyEvents(warehouseEventList,1);
            this.applyEvents(eventList,1);
        }
       eventFiler.startEventLogging();
    }

    public String applyEvents(ArrayList<LinkedHashMap<String, String>> eventList, int applyLocalFlag) throws IOException{
        for(LinkedHashMap<String, String> event : eventList){
            if("add_product_to_shop".equals(event.get("event_type"))){
                System.out.println("Warehouse will add product to shop");
                double size = Double.valueOf(event.get("itemCount"));
                addLotToStock(event, event.get("lotID"),event.get("product_name"), size, applyLocalFlag);
            }
            else if ("order_product".equals(event.get("event_type"))){
                System.out.println("Warehouse will order product");
                orderProduct(event);
            }
            else if("delete_shop".equals(event.get("event_type"))){
                shopProxy.deleteShop(event);
            }
            else if("getEvents".equals(event.get("event_type"))){
                System.out.println("Warehouse will send events");
                return sendEvents(event, Long.parseLong(event.get("timestamp")));
            }
        }
        return null;
    }

    private void orderProduct(LinkedHashMap<String, String> event) {
        WarehouseOrder order = getFromOrders(event.get("orderID"));

        if(order.getProduct()==null){
            WarehouseProduct product = getFromProducts(event.get("product_name"));
            order.setProduct(product)
                .setAddress(event.get("address"))
                .setId(event.get("orderID"));

            warehouse.withOrders(order);
            eventSource.append(event);

            //Reallocate one lot slot.
            Lot lot = product.getLots().get(0);
            double lotSize = lot.getLotSize();
            lot.setLotSize(lotSize-1);
        }
    }

    private WarehouseOrder getFromOrders(String orderID) {
        for(WarehouseOrder order : warehouse.getOrders())
        if(order.getId().equals(orderID)){
            return order;
        }

        return new WarehouseOrder();
    }

    public Lot addLotToStock(LinkedHashMap<String,String> event, String lotId, String product_name, double itemCount, int applyLocalFlag) throws IOException{

        Lot lot = getLot(lotId);
        double oldSize = lot.getLotSize();
        WarehouseProduct warehouseProduct = getFromProducts(product_name);

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
        lot.setLotSize(oldSize+itemCount);
        if(event==null) {
            event = new LinkedHashMap<>();
            event.put("event_type", "add_product_to_shop");
            event.put(".eventKey", lotId);
            event.put("lotID", lotId);
            event.put("product_name", product_name);
            event.put("itemCount", "" + itemCount);
            event.put("old_size", "" + oldSize);
            eventSource.append(event);
        }

        if(oldSize==0.0 && applyLocalFlag==0){
            shopProxy.addProductToShop(event);
        }
        return  lot;
    }

    private WarehouseProduct getFromProducts(String product_name) {

        String productID = product_name.replaceAll("\\W", "");

        for(WarehouseProduct wp : warehouse.getProducts()){
            if(wp.getName().equals(product_name)){
                return wp;
            }
        }
        WarehouseProduct warehouseProduct = new WarehouseProduct()
                .setWarehouse(this.warehouse)
                .setId(productID)
                .setName(product_name);
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
    private String sendEvents(LinkedHashMap<String, String> event, long lastKnownEventTimestamp) {
        System.out.println("sendEvents called");
        SortedMap<Long, LinkedHashMap<String,String>> eventsSince = eventSource.pull(lastKnownEventTimestamp);
        return EventSource.encodeYaml(eventsSince);
    }
}
