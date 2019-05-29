package ha06.Handler;

import ha06.Controller.EditorController;
import ha06.Model.Line;

public class DrawCommandHandler implements CommandLineHandler {

    private EditorController editorController;

    public DrawCommandHandler(EditorController editorController){
        this.editorController=editorController;
    }

    @Override
    public boolean handleCommand(String [] pieces) {
        Line lineObject = new Line();
        lineObject.setName(pieces[1]);
        lineObject.setStartX(Integer.parseInt(pieces[2]));
        lineObject.setStartY(Integer.parseInt(pieces[3]));
        lineObject.setEndX(Integer.parseInt(pieces[4]));
        lineObject.setEndY(Integer.parseInt(pieces[5]));
        editorController.drawObject(lineObject);
        return false;
    }
}
