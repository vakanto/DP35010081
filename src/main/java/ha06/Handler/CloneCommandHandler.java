package ha06.Handler;

import ha06.Controller.EditorController;
import ha06.Model.EuclideanObject;
import ha06.Model.Line;

import java.util.ArrayList;
import java.util.Collection;

public class CloneCommandHandler implements CommandLineHandler{
    private EditorController editorController;

    public CloneCommandHandler(EditorController editorController){
        this.editorController = editorController;
    }

    @Override
    public boolean handleCommand(String[] pieces) {

        EuclideanObject eclideanObject = new EuclideanObject();
        ArrayList<EuclideanObject> objectList = new ArrayList<>();

        try {
            for(int i=1; i<pieces.length-2; i++){
                objectList.add(editorController.getObjectMap().get(pieces[i]));
            }

            objectList=changeObjects(objectList, Double.parseDouble(pieces[pieces.length-2]), Double.parseDouble(pieces[pieces.length-1]));

            for(EuclideanObject object : objectList){
                System.out.println("Drawing object " + object);
                editorController.drawObject(object);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return false;
    }

    private ArrayList<EuclideanObject> changeObjects(ArrayList<EuclideanObject> objectList, double changeX, double changeY){
        System.out.println(objectList.toString());
        ArrayList<EuclideanObject> newObjecList=new ArrayList<>();
        for(EuclideanObject object : objectList){

            if(object.getChildren()==null){
                //is line
                EuclideanObject parent = object.getParent();
                Line line = (Line) object;
                line.setStartX(line.getStartX()+changeX);
                line.setEndX(line.getEndX()+changeX);
                line.setStartY(line.getStartY()+changeY);
                line.setEndY(line.getEndY()+changeY);
                line.setParent(parent);
                newObjecList.add(line);
            }
            else{
                //is complex object
                EuclideanObject newObject = new EuclideanObject();
                newObject.getChildren().addAll(object.getChildren());
                if(object.getParent()!=null){
                    newObject.setParent(object.getParent());
                }
                newObjecList.add(newObject);
                newObject.getChildren().addAll((ArrayList)changeObjects(newObject.getChildren(), changeX, changeY));
            }
        }
        return newObjecList;


    }
}
