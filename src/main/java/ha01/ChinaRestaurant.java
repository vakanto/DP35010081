package ha01;

public class ChinaRestaurant implements Caterer{
    @Override
    public void deliver(String foodNo, String address) {
        System.out.println(foodNo + ": Rice will be delivered to " + address);
    }
}
