package ha06.Handler;

import ha06.Controller.EditorController;
import ha06.Model.EditorModel;

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
        LinkedList<String> tempCommandList =  new LinkedList<>();
        editorModel.getRevertedCommands().add(editorModel.getCommandList().pollLast());
        editorController.processOldCommand("del");
        System.out.println("Cleared Screen");

        for(String command : editorModel.getCommandList()){
            String [] commandParts = command.split(" ");
            if(commandParts[0]=="del"){
                tempCommandList=new LinkedList<>();
            }
            else
            {
                tempCommandList.add(command);
            }
        }

        for(String command : tempCommandList){
            editorController.processOldCommand(command);
        }

        return false;
    }
}
