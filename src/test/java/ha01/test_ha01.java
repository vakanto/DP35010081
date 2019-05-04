package ha01;

import ha01.*;
import org.junit.Test;

// HA01: 07/10
// -1 Das ist kein Test es fehlen Assertions
// -2 Kein gitignore
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
