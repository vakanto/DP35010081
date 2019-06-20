package ha07_ha08;

import com.mashape.unirest.http.exceptions.UnirestException;
import ha07_ha08.Shop.ShopBuilder;
import ha07_ha08.Shop.ShopServer;
import ha07_ha08.Warehouse.WareHouseBuilder;
import ha07_ha08.Warehouse.WarehouseServer;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class test_ha08 {

    @Test
    public void testCommunication() throws IOException, UnirestException, InterruptedException {
        org.apache.log4j.BasicConfigurator.configure();

        File file = new File("src/main/java/ha07_ha08/database/ShopProxy.yml");
        File file2 = new File("src/main/java/ha07_ha08/database/Warehouse.yml");
        File file3 = new File("src/main/java/ha07_ha08/database/WarehouseProxy.yml");

        file.delete();
        file2.delete();
        file3.delete();

        ShopBuilder shopBuilder = new ShopBuilder();
        ShopServer shopServer = new ShopServer();
        WarehouseServer warehouseServer = new WarehouseServer();

        warehouseServer.main(null);
        shopServer.main(null);

        WareHouseBuilder wareHouseBuilder = warehouseServer.wareHouseBuilder;

        sleep(2000);

        wareHouseBuilder.addLotToStock("lot1", "Shoe 42, size 8", 50);

        sleep(1000);

        Assert.assertTrue(wareHouseBuilder.getLot("lot1")!=null);
        Assert.assertTrue(wareHouseBuilder.getLot("lot1").getWareHouseProduct().getName().equals("Shoe 42, size 8"));
        Assert.assertTrue(wareHouseBuilder.getLot("lot1").getLotSize()==50);

        shopBuilder.orderProduct("Shoe 42, size 8", "meineAdresse", "order1");

        System.out.println(wareHouseBuilder.getProductCount("Shoe 42, size 8"));

        Assert.assertTrue(wareHouseBuilder.getProductCount("Shoe 42, size 8")==49.0);

        shopBuilder.orderProduct("Shoe 42, size 8", "meineAdresse", "order1");

        Assert.assertTrue(wareHouseBuilder.getProductCount("Shoe 42, size 8")==48.0);

        //Restart server
        warehouseServer=new WarehouseServer();
        warehouseServer.main(null);
        sleep(1000);
        wareHouseBuilder = warehouseServer.wareHouseBuilder;
        System.out.println(wareHouseBuilder.warehouse.getOrders().get(0).getId());
        Assert.assertTrue(wareHouseBuilder.getProductCount("Shoe 42, size 8")==48.0);
        Assert.assertTrue(wareHouseBuilder.warehouse.getOrders().get(0).getId().equals("order1"));
    }
}
