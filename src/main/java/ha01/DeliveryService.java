package ha01;

public class DeliveryService {

    private Caterer subcontractor;

    public Caterer getSubcontractor() {
        return subcontractor;
    }

    public void setSubcontractor(Caterer subcontractor) {
        this.subcontractor = subcontractor;
    }

    public static void main(String args[])
    {

    }
    public void deliver(String foodNo, String address){
        if(subcontractor==null){
            System.out.println("Subcontractor not defined.");
        }
        System.out.println("Subcontractor is briefed.");
        subcontractor.deliver(foodNo, address);
    }
}
