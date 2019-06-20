package ha07_ha08;

import com.mashape.unirest.http.exceptions.UnirestException;
import ha07_ha08.Shop.Model.ShopProduct;
import ha07_ha08.Shop.ShopBuilder;
import ha07_ha08.Shop.ShopServer;
import ha07_ha08.Warehouse.Model.Lot;
import ha07_ha08.Warehouse.Model.Warehouse;
import ha07_ha08.Warehouse.Model.WarehouseProduct;
import ha07_ha08.Warehouse.WareHouseBuilder;
import ha07_ha08.Warehouse.WarehouseServer;
import org.junit.Assert;
import org.junit.Test;
import sun.rmi.transport.ObjectTable;

import java.io.File;
import java.io.IOException;

import org.fulib.*;

import static java.lang.Thread.sleep;

public class test_ha08 {

    @Test
    public void testCommunication() throws IOException, UnirestException, InterruptedException {
        org.apache.log4j.BasicConfigurator.configure();

        File file = new File("src/main/java/ha07_ha08/database/ShopProxy.yml");
        File file2 = new File("src/main/java/ha07_ha08/database/Warehouse.yml");
        File file3 = new File("src/main/java/ha07_ha08/database/WarehouseProxy.yml");
        File file4 = new File("src/main/java/ha07_ha08/database/Shop.yml");

        file.delete();
        file2.delete();
        file3.delete();
        file4.delete();


        ShopServer shopServer = new ShopServer();
        WarehouseServer warehouseServer = new WarehouseServer();

        warehouseServer.main(null);
        shopServer.main(null);

        WareHouseBuilder wareHouseBuilder = warehouseServer.wareHouseBuilder;
        ShopBuilder shopBuilder = shopServer.shopBuilder;
        sleep(2000);

        wareHouseBuilder.addLotToStock("lot1", "Shoe 42, size 8", 50,0);

        sleep(1000);

        Assert.assertTrue(wareHouseBuilder.getLot("lot1")!=null);
        Assert.assertTrue(wareHouseBuilder.getLot("lot1").getWareHouseProduct().getName().equals("Shoe 42, size 8"));
        Assert.assertTrue(wareHouseBuilder.getLot("lot1").getLotSize()==50);

        shopBuilder.orderProduct("Shoe 42, size 8", "meineAdresse", "order1",0);

        System.out.println(wareHouseBuilder.getProductCount("Shoe 42, size 8"));

        Assert.assertTrue(wareHouseBuilder.getProductCount("Shoe 42, size 8")==49.0);

        shopBuilder.orderProduct("Shoe 42, size 8", "meineAdresse", "order1",0);

        Assert.assertTrue(wareHouseBuilder.getProductCount("Shoe 42, size 8")==48.0);

        //Restart server
        warehouseServer=new WarehouseServer();
        warehouseServer.wareHouseBuilder=new WareHouseBuilder();
        warehouseServer.main(null);
        sleep(2000);
        wareHouseBuilder = warehouseServer.wareHouseBuilder;
        sleep(2000);

        System.out.println(wareHouseBuilder.warehouse.getOrders().get(0).getId());
        Assert.assertTrue(wareHouseBuilder.getProductCount("Shoe 42, size 8")==48.0);
        Assert.assertTrue(wareHouseBuilder.warehouse.getOrders().get(0).getId().equals("order1"));

        System.out.println(shopBuilder.shop.getProducts().size());

        Assert.assertTrue(shopBuilder.shop.getProducts().size()==1);

        System.out.println(shopBuilder.shop.getProducts().get(0).getInStock());

        Assert.assertTrue(shopBuilder.shop.getProducts().get(0).getInStock()==48.0);

        shopServer = new ShopServer();
        sleep(2000);
        shopServer.main(null);

        sleep(1000);
        shopBuilder=shopServer.shopBuilder;

        printWareHouseAndShopProducts(wareHouseBuilder, shopBuilder);
    }

    public void printWareHouseAndShopProducts(WareHouseBuilder wareHouseBuilder, ShopBuilder shopBuilder){
        System.out.println("===============================================================");
        if(wareHouseBuilder!=null){
            System.out.println("Warehouse: " + wareHouseBuilder.warehouse.getName());
            System.out.println("Products: ");
            for(WarehouseProduct product : wareHouseBuilder.warehouse.getProducts()){
                System.out.println(product.getName() + "  " + product.getId());
                for(Lot lot : product.getLots()){
                    System.out.println("Lot : " + lot + "size: " + lot.getLotSize());
                }
            }
        }
        System.out.println("===============================================================");
        if(shopBuilder!=null){
            System.out.println("Shop: " + shopBuilder.shop.getName());
            System.out.println("Products: ");
            for(ShopProduct product : shopBuilder.shop.getProducts()){

                System.out.println("Produktname " + product.getName() + "  " + product.getId());
                System.out.println("Anzahl der Artikel " + product.getInStock());

            }
        }
        System.out.println("===============================================================");
    }
}
