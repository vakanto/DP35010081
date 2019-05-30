package ha06.Handler;

import ha06.Controller.EditorController;
import ha06.Model.EditorModel;
import javafx.application.Platform;

import java.util.Iterator;
import java.util.LinkedList;

public class RedoCommandHandler implements CommandLineHandler{

    private EditorModel editorModel;
    private EditorController editorController;

    public RedoCommandHandler(EditorController editorController){
        this.editorController=editorController;
        this.editorModel=editorController.getEditorModel();
    }

    @Override
    public boolean handleCommand(String[] pieces) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                LinkedList<String>tempList=new LinkedList<>();
                editorModel.getCommandList().removeLast();
                editorModel.getCommandList().add(editorModel.getRevertedCommands().pollLast());

                editorController.processOldCommand(editorModel.getCommandList().getLast());
            }
        });


        return false;
    }
}
