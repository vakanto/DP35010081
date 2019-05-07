package ha04;

public class Visitor {

    public void visit(Unit unit){
        visitKids(unit);
    }

    public void visit(Task task){
        visitKids(task);
    }

    public void visit(Feature feature){
        visitKids(feature);
    }

    protected void visitKids(Unit unit){

        for(Unit u : unit.getChildren()){
            u.accept(this);
        }

    }
}
