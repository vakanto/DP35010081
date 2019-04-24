package ha02;

public class StoryPointCounter extends Visitor{

    private int counter=0;

    @Override
    public void visit(Task task){
        visitKids(task);
    }

    @Override
    public void visit(Feature feature){
        counter+=feature.getStoryPoints();
        visitKids(feature);
    }

    public int getCounter() {

        return counter;
    }

    public void resetCounter(){

        counter=0;
    }
}
