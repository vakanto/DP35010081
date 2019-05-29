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
            EclideanObject object = new EclideanObject();
            object.setName(pieces[1]);
            editorController.drawObject(editorController.getObjectMap().get(object.getName()));
            return true;
        }


        Line lineObject = new Line();
        lineObject.setName(pieces[1]);
        lineObject.setStartX(Integer.parseInt(pieces[2]));
        lineObject.setStartY(Integer.parseInt(pieces[3]));
        lineObject.setEndX(Integer.parseInt(pieces[4]));
        lineObject.setEndY(Integer.parseInt(pieces[5]));

        editorController.getObjectMap().put(pieces[1], lineObject);

        System.out.println("Drawing objects");
        editorController.drawObject(lineObject);
        return false;
    }
}
