package ha06.Controller;

import ha06.Model.EditorModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ResourceBundle;

public class EditorController implements Initializable {

    @FXML
    private TextField commandLine;

    @FXML
    private Pane sheet;

    private EditorModel editorModel;

    public EditorController(){

    }

    public EditorController(EditorModel editorModel){
    }

    public void drawLine(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Line line = new Line();
                line.setStartX(20);
                line.setEndX(100);
                line.setStartY(50);
                line.setEndY(100);

                sheet.getChildren().add(line);
            }
        });
    }

    public void setModel(EditorModel editorModel) {
        this.editorModel=editorModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(sheet);

    }
}
