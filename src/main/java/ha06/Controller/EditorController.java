package ha06.Controller;

import ha06.Handler.*;
import ha06.Model.EuclideanObject;
import ha06.Model.EditorModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditorController implements Initializable {

    @FXML
    private TextField commandLine;

    @FXML
    private Pane sheet;

    private EditorModel editorModel;
    private CommandLineHandler commandLineHandler;
    private HashMap<String, CommandLineHandler> handlers;
    private HashMap<String, EuclideanObject> objectMap;

    public EditorController(){
        editorModel = new EditorModel();
        GroupCommandHandler groupCommandHandler = new GroupCommandHandler(this);
        DrawCommandHandler drawCommandHandler = new DrawCommandHandler(this);
        DeleteCommandHandler deleteCommandHandler = new DeleteCommandHandler(this);
        LineCommandHandler lineCommandHandler = new LineCommandHandler(this);
        CloneCommandHandler cloneCommandHandler = new CloneCommandHandler(this);
        UndoCommandHandler undoCommandHandler = new UndoCommandHandler(this);

        objectMap=new HashMap<>();
        handlers=new HashMap<String, CommandLineHandler>();
        handlers.put("line",lineCommandHandler);
        handlers.put("group",groupCommandHandler);
        handlers.put("draw",drawCommandHandler);
        handlers.put("del", deleteCommandHandler);
        handlers.put("clone", cloneCommandHandler);
        handlers.put("undo", undoCommandHandler);
    }

    public EditorController(EditorModel editorModel){
        editorModel = new EditorModel();
        GroupCommandHandler groupCommandHandler = new GroupCommandHandler(this);
        DrawCommandHandler drawCommandHandler = new DrawCommandHandler(this);
        DeleteCommandHandler deleteCommandHandler = new DeleteCommandHandler(this);
        LineCommandHandler lineCommandHandler = new LineCommandHandler(this);
        CloneCommandHandler cloneCommandHandler = new CloneCommandHandler(this);
        UndoCommandHandler undoCommandHandler = new UndoCommandHandler(this);

        handlers=new HashMap<String, CommandLineHandler>();
        handlers.put("line",lineCommandHandler);
        handlers.put("group",groupCommandHandler);
        handlers.put("draw",drawCommandHandler);
        handlers.put("del", deleteCommandHandler);
        handlers.put("clone", cloneCommandHandler);
        handlers.put("undo", undoCommandHandler);
    }

    @FXML
    public void processCommand(KeyEvent keyEvent){

        if(keyEvent.getCode()!= KeyCode.ENTER){
            //System.out.println("Wrong Button pressed");
            return;
        }
        String command;

        TextField textField = (TextField) keyEvent.getSource();
        command = textField.getText();

        editorModel.getCommandList().add(command);
        System.out.println(command);
        String [] stringParts = command.split(" ");
        CommandLineHandler commandLineHandler = handlers.get(stringParts[0]);
        System.out.println(handlers.get(stringParts));
        commandLineHandler.handleCommand(stringParts);
        commandLine.clear();
    }

    @FXML
    public void processOldCommand(String command){
        String [] stringParts = command.split(" ");
        CommandLineHandler commandLineHandler = handlers.get(stringParts[0]);
        commandLineHandler.handleCommand(stringParts);
        commandLine.clear();
    }

    public void drawLine(Line line){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                sheet.getChildren().add(line);
            }
        });
    }

    public void drawObject(EuclideanObject euclideanObject){
        if(euclideanObject.getChildren()==null){
            //is line
            ha06.Model.Line lineObject = (ha06.Model.Line) euclideanObject;
            Line line = new Line();
            line.setStartX(lineObject.getStartX());
            line.setStartY(lineObject.getStartY());
            line.setEndX(lineObject.getEndX());
            line.setEndY(lineObject.getEndY());

            drawLine(line);
            return;
        }
        //is complex object
        for(EuclideanObject object : euclideanObject.getChildren()){
            if(object!=null){
                drawObject(object);
            }
        }

    }

    public EditorModel getEditorModel() {
        return editorModel;
    }

    public HashMap<String, EuclideanObject> getObjectMap() {
        return objectMap;
    }

    public void setModel(EditorModel editorModel) {
        this.editorModel=editorModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(sheet);

    }

    @FXML
    public void clearScreen() {
        System.out.println(sheet);
        sheet.getChildren().clear();
    }
}
