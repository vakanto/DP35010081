package ha06.Controller;

import ha06.Handler.CommandLineHandler;
import ha06.Handler.DeleteCommandHandler;
import ha06.Handler.DrawCommandHandler;
import ha06.Handler.GroupCommandHandler;
import ha06.Model.EclideanObject;
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
    private HashMap<String, EclideanObject> objectMap;

    public EditorController(){
        GroupCommandHandler groupCommandHandler = new GroupCommandHandler(this);
        DrawCommandHandler drawCommandHandler = new DrawCommandHandler(this);
        DeleteCommandHandler deleteCommandHandler = new DeleteCommandHandler(this);
        objectMap=new HashMap<>();
        handlers=new HashMap<String, CommandLineHandler>();
        handlers.put("line",drawCommandHandler);
        handlers.put("group",groupCommandHandler);
        handlers.put("draw",drawCommandHandler);
        handlers.put("del", deleteCommandHandler);
    }

    public EditorController(EditorModel editorModel){
        GroupCommandHandler groupCommandHandler = new GroupCommandHandler(this);
        DrawCommandHandler drawCommandHandler = new DrawCommandHandler(this);
        DeleteCommandHandler deleteCommandHandler = new DeleteCommandHandler(this);

        handlers=new HashMap<String, CommandLineHandler>();
        handlers.put("line",drawCommandHandler);
        handlers.put("group",groupCommandHandler);
        handlers.put("draw",drawCommandHandler);
        handlers.put("del", deleteCommandHandler);
    }

    @FXML
    public void processCommand(KeyEvent keyEvent){
        if(keyEvent.getCode()!= KeyCode.ENTER){
            System.out.println("Wrong Button pressed");
            return;
        }
        TextField textField = (TextField) keyEvent.getSource();
        String command = textField.getText();
        System.out.println(command);
        String [] stringParts = command.split(" ");
        CommandLineHandler commandLineHandler = handlers.get(stringParts[0]);
        commandLineHandler.handleCommand(stringParts);
        commandLine.clear();
    }

    public void drawLine(Line line){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                /**Line line = new Line();
                line.setStartX(20);
                line.setEndX(100);
                line.setStartY(50);
                line.setEndY(100);**/

                sheet.getChildren().add(line);
            }
        });
    }

    public void drawObject(EclideanObject euclideanObject){
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
        for(EclideanObject object : euclideanObject.getChildren()){
            if(object!=null){
                drawObject(object);
            }
        }

    }

    public HashMap<String, EclideanObject> getObjectMap() {
        return objectMap;
    }

    public void setModel(EditorModel editorModel) {
        this.editorModel=editorModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(sheet);

    }

    public void clearScreen() {
        sheet.getChildren().clear();
    }
}
