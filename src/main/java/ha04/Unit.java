package ha04;

import java.util.ArrayList;
import java.util.UUID;

public class Unit {

    private String type;
    private String name;
    private int storyPoints;
    private ArrayList<Unit> children = new ArrayList<>();
    private ChangeListener listener;
    private Unit parent;
    private UUID uuid;

    Unit(){
        uuid = UUID.randomUUID();
    }

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
            //Get listener from children
            for(Unit u: units){
                if(u.getListener()!=null){
                    this.subscribe(u.getListener());
                }
            }

            for(Unit u : units){
                if(u.getListener()==null){
                    u.subscribe(this.listener);
                }
                    children.add(u);
                    u.setParent(this);
            }
            /**Expand listener to children
            if(this.listener!=null){
                for(Unit c : children){
                    if(c.getListener()==null) {
                        c.subscribe(this.listener);
                    }
                }
            }**/

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
        listener.propertyChange(this.uuid, this, attribute, oldVal, newVal);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void subscribe(ChangeListener listener)
    {
        this.listener = listener;
    }

    public void setParent(Unit parent) {
        if(parent.getListener()!=null){
            this.subscribe(parent.getListener());
        }
        firePropertyChange("parent", this.parent, parent);
        this.parent = parent;
    }

    public ChangeListener getListener() {
        return this.listener;
    }
}
