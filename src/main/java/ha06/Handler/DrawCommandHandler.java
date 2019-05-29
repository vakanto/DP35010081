package ha06.Handler;

import ha06.Controller.EditorController;

public class DrawCommandHandler implements CommandLineHandler {

    private EditorController editorController;

    public DrawCommandHandler(EditorController editorController){
        this.editorController=editorController;
    }

    @Override
    public boolean handleCommand(String [] pieces) {
        editorController.drawLine();
        return false;
    }
}
