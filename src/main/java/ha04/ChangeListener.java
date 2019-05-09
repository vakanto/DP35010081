package ha04;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class ChangeListener {

    //ToDo: Text to Json

    //ToDo: JSon to file
    private File file;
    private int changeCounter;

    ChangeListener(){
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
            JSONArray unitObject = new JSONArray();

                for(Unit u : (ArrayList<Unit>)oldVal){
                    unitObject.add(u);
                }
                jsonObject.put("Change", changeCounter);
                jsonObject.put("Object", unit);
                jsonObject.put("attribute", attribute);
                jsonObject.put("oldVal", oldVal);
                jsonObject.put("units", unitObject);
        }
        else{
                jsonObject.put("changeNr", changeCounter);
                jsonObject.put("Object", unit);
                jsonObject.put("attribute", attribute);
                jsonObject.put("oldVal", oldVal);
                jsonObject.put("newVal", newVal);
        }
        changeCounter++;
    }

    public boolean printJson(JSONObject jsonObject) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
        }
}
