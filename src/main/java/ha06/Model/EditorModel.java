package ha06.Model;

import java.util.LinkedList;
import java.util.Stack;

public class EditorModel {

    private LinkedList<String> commandList;
    private LinkedList<String> revertedCommands;

    public EditorModel(){
        commandList=new LinkedList<>();
        revertedCommands=new LinkedList<>();
    }

    public LinkedList<String> getCommandList() {
        return commandList;
    }

    public void setCommandList(LinkedList<String> commandList) {
        this.commandList = commandList;
    }

    public LinkedList<String> getRevertedCommands() {
        return revertedCommands;
    }

    public void setRevertedCommands(LinkedList<String> revertedCommands) {
        this.revertedCommands = revertedCommands;
    }
}
