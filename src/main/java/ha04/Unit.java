package ha04;

import java.util.ArrayList;
import java.util.Arrays;

public class Unit {

    private String type;
    private String name;
    private int storyPoints;
    private ArrayList<Unit> children = new ArrayList<>();
    private ChangeListener listener;
    private Unit parent;


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
        if(this.type!=null){
            firePropertyChange("type", this.type, type);
        }
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(this.name!=null) {
            firePropertyChange("name", this.name, name);
        }
        this.name = name;
    }

    public void withChildren(Unit... units){
            ArrayList<Unit> tempUnits = new ArrayList<>();
            tempUnits.addAll(children);
            for(Unit u : units){
              children.add(u);
              u.setParent(this);
            }
            if(tempUnits.isEmpty()) {
                firePropertyChange("units", new ArrayList<Unit>(), children);
                return;
            }
            if(children.isEmpty()){
                firePropertyChange("units", tempUnits, new ArrayList<Unit>());
                return;
            }
            firePropertyChange("units", tempUnits, children);
    }

    public ArrayList<Unit> getChildren(){

        return children;
    }

    public Unit getParent() {
        return parent;
    }

    public void firePropertyChange(String attribute, Object oldVal, Object newVal){
        if(listener==null){
            this.listener=ChangeListener.getInstance();
        }
        listener.propertyChange(this, attribute, oldVal, newVal);
    }

    public void subscribe(ChangeListener listener)
    {
        this.listener = listener;
    }

    public void setParent(Unit parent) {
        firePropertyChange("parent", this.parent, parent);
        this.parent = parent;
    }
}
