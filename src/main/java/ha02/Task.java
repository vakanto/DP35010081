package ha02;

public class Task extends Unit {

    private int storyPoint;

    @Override
    public int getStoryPoints() {

        return storyPoint;
    }

    @Override
    public void accept(Visitor visitor){

        visitor.visit(this);
    }
}
