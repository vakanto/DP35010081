package ha07_ha08;

import com.mashape.unirest.http.exceptions.UnirestException;
import ha07_ha08.Shop.ShopBuilder;
import ha07_ha08.Shop.ShopServer;
import ha07_ha08.Warehouse.WareHouseBuilder;
import ha07_ha08.Warehouse.WarehouseServer;
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

        sleep(5000);

        wareHouseBuilder.addLotToStock("lot1", "Shoe 42, size 8", 50);

        sleep(1000);

        shopBuilder.orderProduct("Shoe 42, size 8", "meineAdresse", "order1");

        System.out.println(wareHouseBuilder.getProductCount("Shoe 42, size 8"));
    }
}
