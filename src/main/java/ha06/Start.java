package ha06;

import ha06.Controller.EditorController;
import ha06.Model.EditorModel;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {


    private EditorModel editorModel;
    private EditorController editorController;
    private Parent root;
    private FXMLLoader loader;

    public static void main(String[] args) {
        launch(args);
    }





    @Override
    public void start(Stage primaryStage) throws Exception {
        loader=new FXMLLoader();
        root=loadFXML("Editor.fxml");
        editorModel=new EditorModel();
        primaryStage.setTitle("Editor");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
    }

    private Parent loadFXML(String path) throws Exception{
        Parent parent;
        loader.setLocation(getClass().getResource(path));
        parent=loader.load();
        return parent;
    }
}
