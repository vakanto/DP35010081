package ha07.Warehouse;

import ha07.Shop.Model.Shop;
import ha07.Shop.ShopBuilder;
import ha07.Shop.ShopProxy;
import ha07.Warehouse.Model.Lot;
import ha07.Warehouse.Model.PalettePlace;
import ha07.Warehouse.Model.Warehouse;
import ha07.Warehouse.Model.WarehouseProduct;
import org.fulib.yaml.EventSource;

import java.util.LinkedHashMap;

import static org.fulib.yaml.EventSource.EVENT_KEY;
import static org.fulib.yaml.EventSource.EVENT_TYPE;


public class WareHouseBuilder {
    private Warehouse warehouse;
    private ShopProxy shopProxy;
    private ShopBuilder shopBuilder;
    private EventSource eventSource;

    public WareHouseBuilder(){
        warehouse = new Warehouse();
        shopProxy = new ShopProxy();
        eventSource = new EventSource();
    }

    public Lot addLotToStock(String lotId, String productName, int size){
        Lot lot = getLot(lotId);
        double oldSize = lot.getLotSize();
        WarehouseProduct warehouseProduct = getFromProducts(productName);

        lot.setWareHouseProduct(warehouseProduct);

        if(lot.getPalettePlace()==null){
            for(PalettePlace place : warehouse.getPlaces()){
                if(place.getLot()==null){
                    place.setLot(lot);
                    break;
                }
            }

        }

        LinkedHashMap<String,String> event = new LinkedHashMap<>();
        event.put(EVENT_TYPE,"ADD_LOT_TO_STOCK");
        event.put(EVENT_KEY, lotId);
        event.put("LOT_ID", lotId);
        event.put("PRODUCT", productName);
        event.put("SIZE","" + size);
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
}
