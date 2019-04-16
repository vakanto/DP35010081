package ha01;

public class PizzaShop implements Caterer{
    @Override
    public void deliver(String foodNo, String address) {
        System.out.println(foodNo + ": Pizza Tonno will be delivered to " + address);
    }
}
