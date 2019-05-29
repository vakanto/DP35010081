package ha06.Handler;

import ha06.Controller.EditorController;

public class DeleteCommandHandler implements CommandLineHandler{
    private EditorController editorController;

    public DeleteCommandHandler(EditorController editorController){
        this.editorController=editorController;
    }

    @Override
    public boolean handleCommand(String[] pieces) {
        System.out.println("Clearing screen");
        editorController.clearScreen();
        return false;
    }
}
