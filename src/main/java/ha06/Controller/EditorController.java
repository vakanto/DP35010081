package ha06.Controller;

import ha06.Handler.CommandLineHandler;
import ha06.Handler.DrawCommandHandler;
import ha06.Handler.GroupCommandHandler;
import ha06.Model.EclideanObject;
import ha06.Model.EditorModel;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
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

    public EditorController(){
        GroupCommandHandler groupCommandHandler = new GroupCommandHandler();
        DrawCommandHandler drawCommandHandler = new DrawCommandHandler(this);

        handlers=new HashMap<String, CommandLineHandler>();
        handlers.put("line",drawCommandHandler);
        handlers.put("group",groupCommandHandler);
    }

    public EditorController(EditorModel editorModel){
        GroupCommandHandler groupCommandHandler = new GroupCommandHandler();
        DrawCommandHandler drawCommandHandler = new DrawCommandHandler(this);

        handlers=new HashMap<String, CommandLineHandler>();
        handlers.put("line",drawCommandHandler);
        handlers.put("group",groupCommandHandler);
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
        }
    }

    public void setModel(EditorModel editorModel) {
        this.editorModel=editorModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(sheet);

    }
}
