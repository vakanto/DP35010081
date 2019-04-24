package ha02;

public class Feature extends Unit {

    private int storyPoints;

    Feature(int storyPoints){
        this.storyPoints=storyPoints;
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    @Override
    public int getStoryPoints() {
        return storyPoints;
    }
}
