package ha06.Handler;

import ha06.Controller.EditorController;
import ha06.Model.EditorModel;
import javafx.application.Platform;

import java.util.LinkedList;

public class UndoCommandHandler implements CommandLineHandler {

    private EditorController editorController;
    private EditorModel editorModel;
    public UndoCommandHandler(EditorController editorController){
        this.editorController=editorController;
        this.editorModel=editorController.getEditorModel();
    }

    @Override
    public boolean handleCommand(String[] pieces) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                LinkedList<String> tempCommandList =  new LinkedList<>();
                System.out.println("last command: " + editorModel.getCommandList().getLast());
                editorModel.getRevertedCommands().add(editorModel.getCommandList().pollLast());
                editorModel.getRevertedCommands().add(editorModel.getCommandList().pollLast());
                System.out.println("last command: " + editorModel.getCommandList().getLast());
                editorController.clearScreen();
                System.out.println("Cleared Screen");

                for(String command : editorModel.getCommandList()){
                    String [] commandParts = command.split(" ");
                    if(commandParts[0]=="del"){
                        tempCommandList.clear();
                    }
                    else
                    {
                        tempCommandList.add(command);
                    }
                }

                for(String command : tempCommandList){
                    System.out.println("Repeating command: " + command);
                    editorController.processOldCommand(command);
                }


            }
        });
        return false;
    }
}
