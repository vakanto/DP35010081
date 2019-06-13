package ha07;

import com.mashape.unirest.http.exceptions.UnirestException;
import ha07.Shop.ShopServer;
import ha07.Warehouse.WareHouseBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class test_ha07 {

    @Test
    public void testAddProductToShop() throws IOException, UnirestException, InterruptedException {
        WareHouseBuilder wareHouseBuilder = new WareHouseBuilder();
        ShopServer shopServer = new ShopServer();
        shopServer.main(null);

        sleep(1000);

        wareHouseBuilder.addLotToStock("lot1", "Shoe 42, size 8", 50);

        Assert.assertTrue(shopServer.shopBuilder.getFromProducts("Shoe42size8").getInStock()==50);
    }
}
