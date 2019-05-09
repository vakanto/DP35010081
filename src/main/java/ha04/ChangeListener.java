package ha04;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ChangeListener {

    //ToDo: Text to Json
    private org.json.JSONObject jsonWrapper;
    //ToDo: JSon to file
    private File file;

    private int changeCounter;
    private static ChangeListener changeListener;
    ChangeListener(){
        //jsonObject=new JSONObject();
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

    public void propertyChange(UUID uuid, Unit unit, String attribute, Object oldVal, Object newVal)
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
                jsonObject.put("UUID", uuid);
                jsonObject.put("ObjectClass", unit.getName());
                jsonObject.put("JavaClass", unit.getClass().getCanonicalName());
                jsonObject.put("Timestamp", System.nanoTime());
                jsonObject.put("attribute", attribute);
                jsonObject.put("oldVal", oldUnits);
                jsonObject.put("newVal", newUnits);
                printJson(jsonObject);
        }
        else{
                jsonObject.put("Object", unit);
                jsonObject.put("UUID", uuid);
                jsonObject.put("ObjectClass", unit.getName());
                jsonObject.put("JavaClass",  unit.getClass().getCanonicalName());
                jsonObject.put("Timestamp", System.nanoTime());
                jsonObject.put("attribute", attribute);
                jsonObject.put("oldVal", oldVal);
                jsonObject.put("newVal", newVal);
                printJson(jsonObject);
        }
        changeCounter++;
    }

    public boolean printJson(JSONObject jsonObject) {
        FileWriter writer;
        try {
            try {
                String fileContents = new String(Files.readAllBytes(Paths.get(file.getPath())));
                jsonWrapper = new org.json.JSONObject(fileContents);
            } catch (JSONException e) {
                System.out.println("TEst");
                jsonWrapper=new org.json.JSONObject();
            }

            try {
                org.json.JSONObject formatObject = new org.json.JSONObject(jsonObject);
                jsonWrapper.append("ChangeNr" + changeCounter, formatObject);
                writer = new FileWriter(file);
                writer.write(jsonWrapper.toString(2));
                writer.close();
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
