package ha06.Handler;

import ha06.Controller.EditorController;
import ha06.Model.EclideanObject;
import ha06.Model.Line;

public class DrawCommandHandler implements CommandLineHandler {

    private EditorController editorController;

    public DrawCommandHandler(EditorController editorController){
        this.editorController=editorController;
    }

    @Override
    public boolean handleCommand(String [] pieces) {


        if(pieces.length==2){
            //is group or single line
            EclideanObject object = editorController.getObjectMap().get(pieces[1]);
            editorController.drawObject(editorController.getObjectMap().get(pieces[1]));
            return true;
        }

        return false;
    }
}
