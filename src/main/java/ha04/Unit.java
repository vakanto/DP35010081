package ha04;

import java.util.ArrayList;

public class Unit {

    private String type;
    private String name;
    private int storyPoints;
    private ArrayList<Unit> children = new ArrayList<>();
    private ChangeListener listener;


    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        firePropertyChange("type", this.type, type);
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        firePropertyChange("name", this.name, name);
        this.name = name;
    }

    public void withChildren(Unit... units){

        for(Unit unit : units){
            children.add(unit);
            firePropertyChange("units", units, unit);
        }
    }

    public ArrayList<Unit> getChildren(){

        return children;
    }

    public void firePropertyChange(String attribute, Object oldVal, Object newVal){
        listener.propertyChange(this, attribute, oldVal, newVal);
    }

    public void subscribe(ChangeListener listener)
    {
        this.listener = listener;
    }
}
