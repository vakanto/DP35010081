package ha06.Handler;

import ha06.Controller.EditorController;
import ha06.Model.Line;

public class LineCommandHandler implements CommandLineHandler{

    private EditorController editorController;

    public LineCommandHandler(EditorController editorController){
        this.editorController=editorController;
    }

    @Override
    public boolean handleCommand(String[] pieces) {


        try {
            Line lineObject = new Line();
            lineObject.setName(pieces[1]);
            lineObject.setStartX(Integer.parseInt(pieces[2]));
            lineObject.setStartY(Integer.parseInt(pieces[3]));
            lineObject.setEndX(Integer.parseInt(pieces[4]));
            lineObject.setEndY(Integer.parseInt(pieces[5]));

            editorController.getObjectMap().put(pieces[1], lineObject);

            System.out.println("Drawing objects");
            editorController.drawObject(lineObject);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
