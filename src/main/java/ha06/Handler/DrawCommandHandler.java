package ha06.Handler;

import ha06.Controller.EditorController;
import ha06.Model.EuclideanObject;

public class DrawCommandHandler implements CommandLineHandler {

    private EditorController editorController;

    public DrawCommandHandler(EditorController editorController){
        this.editorController=editorController;
    }

    @Override
    public boolean handleCommand(String [] pieces) {


            //is group or single line
            EuclideanObject object = editorController.getObjectMap().get(pieces[1]);
            editorController.drawObject(editorController.getObjectMap().get(pieces[1]));
            return true;

    }
}
