package ha04;

import org.junit.Assert;
import org.junit.Test;


public class test_ha04 {

    @Test
    public void test() {
        ChangeListener changeListener = new ChangeListener();

        Unit release1 = new Unit();

        //subscribe the listener to one Object
        release1.subscribe(changeListener);
        release1.setType("Release");
        release1.setName("WT1819");

        Assert.assertEquals(release1.getListener(), changeListener);

        Unit release2 = new Unit();
        release2.setType("Release");
        release2.setName("ST19");

        Unit sprint1 = new Unit();
        release1.withChildren(sprint1);
        sprint1.setName("Sprint1");
        sprint1.setType("Sprint");
        sprint1.withChildren(new Feature(8), new Feature(8), new Feature(8));

        //Test if all children an neighbour nodes got an listener object.
        Assert.assertEquals(sprint1.getListener(),changeListener);
        for(Unit child : sprint1.getChildren()){
            Assert.assertEquals(child.getListener(),changeListener);
        }

        Unit sprint2 = new Unit();
        release1.withChildren(sprint2);
        //sprint2.subscribe(changeListener);
        sprint2.setName("Sprint2");
        sprint2.setType("Sprint");
        sprint2.withChildren(new Feature(8), new Feature(8), new Feature(8));

        //Test if parent object get the listener object from their children
        Assert.assertEquals(sprint1.getListener(),changeListener);

        Unit sprint3 = new Unit();
        release1.withChildren(sprint3);
        sprint3.setName("Sprint3");
        sprint3.setType("Sprint");
        sprint3.withChildren(new Feature(8), new Feature(8), new Feature(8));

        Unit sprint4 = new Unit();
        release1.withChildren(sprint4);
        sprint4.setName("Sprint4");
        sprint4.setType("Sprint");
        sprint4.withChildren(new Feature(8), new Feature(8), new Feature(8));

        Unit sprint5 = new Unit();
        release1.withChildren(sprint5);
        sprint5.setName("Sprint5");
        sprint5.setType("Sprint");
        sprint5.withChildren(new Feature(8), new Feature(8), new Feature(8));

        Unit sprint6 = new Unit();
        release1.withChildren(sprint6);
        sprint6.setName("Sprint3");
        sprint6.setType("Sprint");
        sprint6.withChildren(new Feature(8), new Feature(8), new Feature(8));

        StoryPointCounter storyPointCounter = new StoryPointCounter();


        release2.withChildren(sprint4,sprint5,sprint6);

        release1.accept(storyPointCounter);
        release2.accept(storyPointCounter);

        Unit epic = new Unit();
        epic.setType("Epic");
        epic.setName("Epic");
        epic.withChildren(release1, release2);

        Assert.assertEquals(epic.getListener(), changeListener);

        storyPointCounter.resetCounter();
        epic.accept(storyPointCounter);

        Feature feature = (Feature) sprint6.getChildren().get(sprint6.getChildren().size()-1);
        Unit task1=new Unit();
        task1.setType("Task");
        task1.setName("task1");

        sprint1.withChildren(task1);

        Unit task2=new Unit();
        task2.setType("Task");
        task2.setName("task2");

        Unit task3=new Unit();
        task3.setType("Task");
        task3.setName("task3");

        Unit task4=new Unit();
        task4.setType("Task");
        task4.setName("task4");

        feature.withChildren(task1,task2,task3,task4);

        storyPointCounter.resetCounter();
        epic.accept(storyPointCounter);

        Unit docu = new Unit();
        docu.setType("Task");
        docu.setName("Docu");

        epic.withChildren(docu);

        storyPointCounter.resetCounter();
        epic.accept(storyPointCounter);

        Assert.assertEquals(epic.getListener(), changeListener);
    }

}
