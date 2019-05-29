package ha06.Model;

import java.util.ArrayList;

public class EuclideanObject {
    private String type;
    private String name;
    private ArrayList<EuclideanObject> children = new ArrayList<>();
    private EuclideanObject parent;

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

    public ArrayList<EuclideanObject> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<EuclideanObject> children) {
        this.children = children;
    }

    public EuclideanObject getParent() {
        return parent;
    }

    public void setParent(EuclideanObject parent) {
        this.parent = parent;
        System.out.println("parent set");
        if(parent.getChildren()==null){
           return;
        }
        if(!parent.getChildren().contains(this)){
            parent.getChildren().add(this);
        }
    }
}
