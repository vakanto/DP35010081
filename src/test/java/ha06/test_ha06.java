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

        clickOn(commandLine).write("line l1 10 10 500 10");
        clickOn(commandLine).push(KeyCode.ENTER);
        clickOn(commandLine).write("line l2 10 20 500 20");
        clickOn(commandLine).push(KeyCode.ENTER);


        clickOn(commandLine).write("group g1 l1 l2");
        clickOn(commandLine).push(KeyCode.ENTER);
        clickOn(commandLine).write("del");
        clickOn(commandLine).push(KeyCode.ENTER);


        clickOn(commandLine).write("draw g1");
        clickOn(commandLine).push(KeyCode.ENTER);

        sleep(2000);

    }
}
