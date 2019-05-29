package ha06.Handler;

import ha06.Controller.EditorController;
import ha06.Handler.CommandLineHandler;
import ha06.Model.EclideanObject;

import java.util.ArrayList;

public class GroupCommandHandler implements CommandLineHandler {
    private EditorController editorController;
    private ArrayList<ha06.Model.EclideanObject> objectList;
    @Override
    public boolean handleCommand(String[] pieces) {
        for(int i=1; i<pieces.length;i++){

            objectList.add()
        }

        return false;
    }
}
