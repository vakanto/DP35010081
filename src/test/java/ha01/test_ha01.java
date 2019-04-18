package ha01;

import ha01.*;
import org.junit.Test;

public class test_ha01 {
    @Test
    public void test(){
        DeliveryService deliveryService = new DeliveryService();

        Caterer subcontractor = new PizzaShop();
        deliveryService.setSubcontractor(subcontractor);
        deliveryService.deliver("m42", "WilliAllee 73");

        subcontractor = new BurgerBude();
        deliveryService.setSubcontractor(subcontractor);
        deliveryService.deliver("m42","WilliAllee 73");

        subcontractor = new DoenerLaden();
        deliveryService.setSubcontractor(subcontractor);
        deliveryService.deliver("m42","WilliAllee 73");

        subcontractor = new ChinaRestaurant();
        deliveryService.setSubcontractor(subcontractor);
        deliveryService.deliver("m42","WilliAllee 73");
    }
}
