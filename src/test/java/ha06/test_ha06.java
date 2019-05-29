package ha06;

import ha05.EmbeddedLauncher;
import ha05.customer_client.Customer_Client;
import ha05.taxi_client.Taxi_Client;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.testfx.framework.junit.ApplicationTest;
import javafx.stage.Stage;
import org.junit.Test;

public class test_ha06 extends ApplicationTest {

    private Stage editor;
    private Start startApplication;

    @Override
    public void start(Stage stage) throws Exception {
        editor=stage;
        startApplication = new Start();
        startApplication.start(editor);
    }


    @Test
    public void testInput(){
        TextField commandLine = (TextField) lookup("#commandLine").query();

        clickOn(commandLine).write("line l1 10 235 110 235");
        clickOn(commandLine).push(KeyCode.ENTER);
        clickOn(commandLine).write("line l2 10 135 10 235");
        clickOn(commandLine).push(KeyCode.ENTER);
        clickOn(commandLine).write("line l3 10 135 110 135");
        clickOn(commandLine).push(KeyCode.ENTER);
        clickOn(commandLine).write("line l4 110 135 110 235");
        clickOn(commandLine).push(KeyCode.ENTER);
        clickOn(commandLine).write("line l5 10 135 110 235");
        clickOn(commandLine).push(KeyCode.ENTER);
        clickOn(commandLine).write("line l6 110 135 10 235");
        clickOn(commandLine).push(KeyCode.ENTER);
        clickOn(commandLine).write("line l7 10 135 60 95");
        clickOn(commandLine).push(KeyCode.ENTER);
        clickOn(commandLine).write("line l8 110 135 60 95");
        clickOn(commandLine).push(KeyCode.ENTER);

        clickOn(commandLine).write("group g1 l1 l2 l3 l4 l5 l6 l7 l8");
        clickOn(commandLine).push(KeyCode.ENTER);
        clickOn(commandLine).write("del");
        clickOn(commandLine).push(KeyCode.ENTER);


        clickOn(commandLine).write("draw g1");
        clickOn(commandLine).push(KeyCode.ENTER);

        //sleep(2000);

    }
}
