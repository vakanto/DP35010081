package ha04;

public class TaskCounter extends Visitor {

    private int counter = 0;

    @Override
    public void visit(Task task){
        counter +=1;
        this.visitKids(task);
    }

    @Override
    public void visit(Feature feature){

    }
}
