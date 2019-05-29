package ha06.Model;

import java.util.ArrayList;

public class Line extends EclideanObject {
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return endX;
    }


    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    @Override
    public void setChildren(ArrayList<EclideanObject> children) {
        return;
    }

    @Override
    public ArrayList<EclideanObject> getChildren() {
        return null;
    }
}
