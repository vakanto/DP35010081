package ha07;

import com.mashape.unirest.http.exceptions.UnirestException;
import ha07.Shop.ShopServer;
import ha07.Warehouse.WareHouseBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static java.lang.Thread.sleep;

// HA07: 10/10
public class test_ha07 {

    @Test
    public void testAddProductToShop() throws IOException, UnirestException, InterruptedException {
        org.apache.log4j.BasicConfigurator.configure();

        WareHouseBuilder wareHouseBuilder = new WareHouseBuilder();
        ShopServer shopServer = new ShopServer();
        shopServer.main(null);

        sleep(1000);

        wareHouseBuilder.addLotToStock("lot1", "Shoe 42, size 8", 50);

        //wait for server to compute
        sleep(5000);

        System.out.println(ShopServer.shopBuilder.getFromProducts("Shoe 42, size 8").getInStock());

        //Check the warehousebuilder
        Assert.assertTrue(wareHouseBuilder.getLot("lot1")!=null);
        Assert.assertTrue(wareHouseBuilder.getLot("lot1").getWareHouseProduct().getName().equals("Shoe 42, size 8"));
        Assert.assertTrue(wareHouseBuilder.getLot("lot1").getLotSize()==50);

        //Check transmission to ShopServer
        Assert.assertTrue(ShopServer.shopBuilder.getFromProducts("Shoe 42, size 8").getInStock()==50);
        Assert.assertTrue(ShopServer.shopBuilder.getFromProducts("Shoe 42, size 8").getId().equals("Shoe42size8"));

        wareHouseBuilder.addLotToStock("lot2", "Shoe 42, size 8", 50);
        System.out.println(ShopServer.shopBuilder.getFromProducts("Shoe 42, size 8").getInStock());
        wareHouseBuilder.addLotToStock("lot1", "Shoe 42, size 8", 100);
    }
}
