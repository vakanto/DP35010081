package ha07_ha08.Warehouse;

import com.mashape.unirest.http.exceptions.UnirestException;
import ha07_ha08.Shop.ShopBuilder;
import ha07_ha08.Warehouse.Model.*;
import org.fulib.yaml.EventFiler;
import org.fulib.yaml.EventSource;
import org.fulib.yaml.Yamler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class WareHouseBuilder {

    public Warehouse warehouse;
    private ShopProxy shopProxy;
    private EventSource eventSource;
    private EventFiler eventFiler;

    public WareHouseBuilder() throws IOException, UnirestException {
        warehouse = new Warehouse()
            .setName("DasWarenhaus");
        shopProxy = new ShopProxy();
        eventSource = new EventSource();
        eventFiler = new EventFiler(eventSource)
            .setHistoryFileName("src/main/java/ha07_ha08/database/Warehouse.yml");

        String history = eventFiler.loadHistory();
        if(history!=null){
            //LinkedHashMap<String,String>event = new LinkedHashMap<>();
            //event.put("event_type","delete_shop");
            //shopProxy.deleteShop(event);
            //ArrayList<LinkedHashMap<String,String>> eventList = new Yamler().decodeList(history);
            //shopProxy.sendRequest(EventSource.encodeYaml(event));
            //this.applyEvents(eventList);
            //eventList.remove(0);
            //shopProxy.sendLoadedEvents(eventList);
            File file = new File("src/main/java/ha07_ha08/database/Warehouse.yml");
            file.delete();
            file.createNewFile();
            ArrayList<LinkedHashMap<String,String>> eventList = new Yamler().decodeList(shopProxy.loadEvents());
            this.applyEvents(eventList,1);
        }
        eventFiler.startEventLogging();
    }

    public void applyEvents(ArrayList<LinkedHashMap<String, String>> eventList, int applyLocalFlag) throws IOException, UnirestException {
        for(LinkedHashMap<String, String> event : eventList){
            if("add_product_to_shop".equals(event.get("event_type"))){
                double size = Double.valueOf(event.get("itemCount"));
                addLotToStock(event.get("lotID"),event.get("product_name"), size, applyLocalFlag);
            }
            else if ("order_product".equals(event.get("event_type"))){
                orderProduct(event.get("event_key"),event.get("product_name"), event.get("address"));
            }
            else if("delete_shop".equals(event.get("event_type"))){
                shopProxy.deleteShop(event);
            }
        }
    }

    private void orderProduct(String orderID, String product_name, String address) {
        WarehouseOrder order = getFromOrders(orderID);

        if(order.getProduct()==null){
            WarehouseProduct product = getFromProducts(product_name);
            order.setProduct(product)
                .setAddress(address)
                .setId(orderID);

            warehouse.withOrders(order);

            LinkedHashMap<String,String>event = new LinkedHashMap<>();
            event.put("event_type","order_product");
            event.put("event_key", orderID);
            event.put("product_name",product_name);
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

    public Lot addLotToStock(String lotId, String product_name, double itemCount, int applyLocalFlag) throws IOException, UnirestException {

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
        lot.setLotSize(itemCount);
        LinkedHashMap<String,String> event = new LinkedHashMap<>();
        event.put("event_type","add_product_to_shop");
        event.put("event_key", lotId);
        event.put("lotID", lotId);
        event.put("product_name", product_name);
        event.put("itemCount","" + itemCount);
        event.put("old_size", ""+oldSize);
        eventSource.append(event);

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
}
