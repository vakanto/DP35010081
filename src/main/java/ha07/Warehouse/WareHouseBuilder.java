package ha07.Warehouse;

import ha07.Shop.ShopProxy;
import ha07.Warehouse.Model.Lot;
import ha07.Warehouse.Model.Warehouse;
import ha07.Warehouse.Model.WarehouseProduce;
import ha07.Warehouse.Model.WarehouseProduct;
import org.fulib.yaml.EventSource;

import java.util.ArrayList;

public class WareHouseBuilder {
    private Warehouse warehouse;
    private ShopProxy shopProxy;
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



        return  lot;
    }

    private WarehouseProduct getFromProducts(String productName) {
        for(WarehouseProduct wp : warehouse.getProducts()){
            if(wp.getName().equals(productName)){
                return wp;
            }
        }
        return null;
    }

    public Lot getLot(String lotId){
        Lot lot = new Lot();

        return  lot;
    }
}
