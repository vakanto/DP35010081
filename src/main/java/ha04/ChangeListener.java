package ha04;

import java.util.ArrayList;
import java.util.List;
import org.json.*;


public class ChangeListener {

    //ToDo: Text to Json

    //ToDo: JSon to file

    public void propertyChange(String attribute, Object oldVal, Object newVal)
    {
        if(oldVal instanceof List<?>){
            JSONObject jsonObject = new JSONObject();
            JSONObject unitObject = new JSONObject();

            jsonObject.put("units", new String[]{});
            for(Unit unit : (ArrayList<Unit>)oldVal){
                unitObject.put(unit.getName(), unit);
            }

            printJson(jsonObject);
        }
    }

    private void printJson(JSONObject jsonObject) {

    }


}
