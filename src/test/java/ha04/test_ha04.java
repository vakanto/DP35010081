package ha04;

import ha04.Feature;
import ha04.StoryPointCounter;
import ha04.Unit;
import javafx.scene.control.TextFormatter;
import org.junit.Test;


public class test_ha04 {

    @Test
    public void test() {
        ChangeListener changeListener = new ChangeListener();

        Unit release1 = new Unit();
        release1.subscribe(changeListener);
        release1.setType("Release");
        release1.setName("WT1819");

        Unit release2 = new Unit();
        release2.subscribe(changeListener);
        release2.setType("Release");
        release2.setName("ST19");

        Unit sprint1 = new Unit();
        sprint1.subscribe(changeListener);
        sprint1.setName("Sprint1");
        sprint1.setType("Sprint");
        sprint1.withChildren(new Feature(8), new Feature(8), new Feature(8));

        Unit sprint2 = new Unit();
        sprint2.subscribe(changeListener);
        sprint2.setName("Sprint2");
        sprint2.setType("Sprint");
        sprint2.withChildren(new Feature(8), new Feature(8), new Feature(8));

        Unit sprint3 = new Unit();
        sprint3.subscribe(changeListener);
        sprint3.setName("Sprint3");
        sprint3.setType("Sprint");
        sprint3.withChildren(new Feature(8), new Feature(8), new Feature(8));

        Unit sprint4 = new Unit();
        sprint4.subscribe(changeListener);
        sprint4.setName("Sprint4");
        sprint4.setType("Sprint");
        sprint4.withChildren(new Feature(8), new Feature(8), new Feature(8));

        Unit sprint5 = new Unit();
        sprint5.subscribe(changeListener);
        sprint5.setName("Sprint5");
        sprint5.setType("Sprint");
        sprint5.withChildren(new Feature(8), new Feature(8), new Feature(8));

        Unit sprint6 = new Unit();
        sprint6.subscribe(changeListener);
        sprint6.setName("Sprint3");
        sprint6.setType("Sprint");
        sprint6.withChildren(new Feature(8), new Feature(8), new Feature(8));

        StoryPointCounter storyPointCounter = new StoryPointCounter();

        int storyPoints=0;

        release1.withChildren(sprint1,sprint2,sprint3);
        release2.withChildren(sprint4,sprint5,sprint6);

        release1.accept(storyPointCounter);
        release2.accept(storyPointCounter);
        storyPoints+=storyPointCounter.getCounter();


        //Gesamtzahl StoryPoints
        System.out.println("Aufgabe c " + storyPoints);


        //d
        Unit epic = new Unit();
        epic.subscribe(changeListener);
        epic.setType("Epic");
        epic.setName("Epic");
        epic.withChildren(release1, release2);

        storyPointCounter.resetCounter();
        epic.accept(storyPointCounter);

        System.out.println("Aufgabe d " + storyPointCounter.getCounter());

        //e
        Feature feature = (Feature) sprint6.getChildren().get(sprint6.getChildren().size()-1);
        Unit task1=new Unit();
        task1.subscribe(changeListener);
        task1.setType("Task");
        task1.setName("task1");

        Unit task2=new Unit();
        task2.subscribe(changeListener);
        task2.setType("Task");
        task2.setName("task2");

        Unit task3=new Unit();
        task3.subscribe(changeListener);
        task3.setType("Task");
        task3.setName("task3");

        Unit task4=new Unit();
        task4.subscribe(changeListener);
        task4.setType("Task");
        task4.setName("task4");

        feature.withChildren(task1,task2,task3,task4);

        storyPointCounter.resetCounter();
        epic.accept(storyPointCounter);
        System.out.println("Aufgabe e " +storyPointCounter.getCounter());

        //f
        Unit docu = new Unit();
        docu.subscribe(changeListener);
        docu.setType("Task");
        docu.setName("Docu");

        epic.withChildren(docu);

        storyPointCounter.resetCounter();
        epic.accept(storyPointCounter);
        System.out.println("Aufgabe f " + storyPointCounter.getCounter());

        changeListener.printJson();
    }

}
