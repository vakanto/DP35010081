package ha01;

public class DoenerLaden implements Caterer {
    @Override
    public void deliver(String foodNo, String address) {
        System.out.println(foodNo + ": Spicy Döner will be delivered to " + address);
    }
}
