package ha06.Handler;

import ha06.Controller.EditorController;
import ha06.Model.EuclideanObject;

import java.util.ArrayList;

public class GroupCommandHandler implements CommandLineHandler {
    private EditorController editorController;
    private ArrayList<EuclideanObject> objectList;

    public GroupCommandHandler(EditorController editorController){
        this.editorController=editorController;
    }

    @Override
    public boolean handleCommand(String[] pieces) {
        objectList = new ArrayList<>();

        try {
            EuclideanObject group = new EuclideanObject();
            System.out.println(pieces);
            for(int i=2; i<pieces.length;i++){
                //Causion: Object cannot be in map
                objectList.add(editorController.getObjectMap().get(pieces[i]));
                System.out.println("Added");
                editorController.getObjectMap().get(pieces[i]).setParent(group);
                System.out.println("Added parent");
            }

            group.setChildren(objectList);
            //Causion: malformed inputs are not handled at all
            group.setName(pieces[1]);
            System.out.println("Grouped objects " + group.getName());
            editorController.getObjectMap().put(group.getName(), group);

            return true;
        } catch (Exception e) {
            System.out.println("Failed");
            return false;
        }

    }
}
