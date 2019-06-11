package ha06;

import ha05.EmbeddedLauncher;
import ha05.customer_client.Customer_Client;
import ha05.taxi_client.Taxi_Client;
import ha06.Controller.EditorController;
import ha06.Model.EditorModel;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.Assert;
import org.testfx.framework.junit.ApplicationTest;
import javafx.stage.Stage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
// HA06: 08/10
// -2 java.util.NoSuchElementException
//	at java.util.Optional.orElseThrow(Optional.java:290)
//	at org.testfx.service.finder.impl.WindowFinderImpl.window(WindowFinderImpl.java:70)
//	at org.testfx.robot.impl.WriteRobotImpl.fetchTargetWindow(WriteRobotImpl.java:86)
//	at org.testfx.robot.impl.WriteRobotImpl.write(WriteRobotImpl.java:78)
//	at org.testfx.robot.impl.WriteRobotImpl.write(WriteRobotImpl.java:73)
//	at org.testfx.api.FxRobot.write(FxRobot.java:506)
//	at org.testfx.api.FxRobot.write(FxRobot.java:60)
//	at ha06.test_ha06.testInput(test_ha06.java:68)
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
        EditorController editorController = startApplication.getEditorController();
        EditorModel editorModel = editorController.getEditorModel();
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("line l1 10 235 110 235");
        commandList.add("line l2 10 135 10 235");
        commandList.add("line l3 10 135 110 135");
        commandList.add("line l4 110 135 110 235");
        commandList.add("line l5 10 135 110 235");
        commandList.add("line l6 110 135 10 235");
        commandList.add("line l7 10 135 60 95");
        commandList.add("line l8 110 135 60 95");
        commandList.add("group g1 l1 l2 l3 l4 l5 l6 l7 l8");
        commandList.add("del");
        commandList.add("draw g1");
        commandList.add("clone g1 150 0");
        commandList.add("clone g1 150 0");
        commandList.add("undo");
        commandList.add("undo");
        commandList.add("redo");
        commandList.add("redo");


        for(String command : commandList){
            String [] commandParts = command.split(" ");
            String lastCommand = null;
            String lastRevertedCommand=null;
            if(!editorModel.getCommandList().isEmpty()){
                lastCommand = editorModel.getCommandList().getLast();
            }
            if(!editorModel.getRevertedCommands().isEmpty()){
                lastCommand = editorModel.getRevertedCommands().getLast();
            }

            clickOn(commandLine).write(command);
            clickOn(commandLine).push(KeyCode.ENTER);

            if(commandParts[0]=="undo" && lastCommand!=null){
                Assert.assertEquals(lastCommand, editorModel.getRevertedCommands().getLast());
            }
            else if(commandParts[0]=="redo"){
                Assert.assertEquals(lastCommand, editorModel.getCommandList().getLast());
            }
            else {
                Assert.assertEquals(command, editorModel.getCommandList().getLast());
            }
        }
    }
}
