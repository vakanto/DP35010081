package ha07.Warehouse;

import ha07.Shop.ShopProxy;
import ha07.Warehouse.Model.Lot;
import ha07.Warehouse.Model.PalettePlace;
import ha07.Warehouse.Model.Warehouse;
import ha07.Warehouse.Model.WarehouseProduct;
import org.fulib.yaml.EventSource;


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

        lot.setWareHouseProduct(warehouseProduct);

        if(lot.getPalettePlace()==null){
            for(PalettePlace place : warehouse.getPlaces()){
                if(place.getLot()==null){
                    place.setLot(lot);
                    break;
                }
            }

        }

        return  lot;
    }

    private WarehouseProduct getFromProducts(String productName) {
        String productID = productName.replaceAll(" ", "");

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
