package ha06.Model;

import ha02.Unit;

import java.util.ArrayList;

public class EclideanObject {
    private String type;
    private String name;
    private ArrayList<EclideanObject> children = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<EclideanObject> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<EclideanObject> children) {
        this.children = children;
    }
}
