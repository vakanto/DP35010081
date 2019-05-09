package ha04;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.*;
import org.json.JSONWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class ChangeListener {

    //ToDo: Text to Json
    private JSONObject jsonObject;
    //ToDo: JSon to file
    private File file;
    private int changeCounter;
    private static ChangeListener changeListener;
    ChangeListener(){
        jsonObject=new JSONObject();
        changeCounter=0;
        file = new File("src/main/java/ha04/files/changeLog.json");
        try {
            if(file.createNewFile())
            {
                System.out.println("File successfully created.");
            }else{
                System.out.println("File allready exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void propertyChange(Unit unit, String attribute, Object oldVal, Object newVal)
    {
        JSONObject jsonObject = new JSONObject();
        if(oldVal instanceof List<?> && newVal instanceof List<?>){
            JSONArray oldUnits = new JSONArray();
            JSONArray newUnits = new JSONArray();

                for(Unit u : (ArrayList<Unit>)oldVal){
                    oldUnits.add(u);
                }
                for(Unit u : (ArrayList<Unit>)newVal){
                    newUnits.add(u);
                }
                jsonObject.put("Object", unit);
                jsonObject.put("ObjectClass", unit.getName());
                jsonObject.put("attribute", attribute);
                jsonObject.put("oldVal", oldUnits);
                jsonObject.put("newVal", newUnits);
                this.jsonObject.put("Change " + changeCounter, jsonObject);
        }
        else{
                jsonObject.put("Object", unit);
                jsonObject.put("ObjectClass", unit.getName());
                jsonObject.put("attribute", attribute);
                jsonObject.put("oldVal", oldVal);
                jsonObject.put("newVal", newVal);
                this.jsonObject.put("Change " + changeCounter, jsonObject);
        }
        changeCounter++;
    }

    public boolean printJson() {
        try {
            FileWriter writer = new FileWriter(file);
            org.json.JSONObject formatObject = new org.json.JSONObject(this.jsonObject);
            try {
                writer.append(formatObject.toString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
        }

    public static ChangeListener getInstance(){
        if(changeListener==null){
            changeListener=new ChangeListener();
        }
        return changeListener;
    }
}
