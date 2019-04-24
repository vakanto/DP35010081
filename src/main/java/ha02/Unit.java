package ha02;

import java.util.ArrayList;
import java.util.HashSet;

public class Unit {

    private String type;
    private String name;
    private int storyPoints;
    private ArrayList<Unit> children = new ArrayList<>();

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
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void withChildren(Unit ... units){

        for(Unit unit : units){
            children.add(unit);
        }
    }

    public ArrayList<Unit> getChildren(){
        return children;
    }
}
