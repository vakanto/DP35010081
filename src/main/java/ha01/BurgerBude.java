package ha01;

public class BurgerBude implements Caterer{

    @Override
    public void deliver(String foodNo, String address) {
        System.out.println(foodNo + ": Cheese Burger will be delivered to " + address);
    }
}
