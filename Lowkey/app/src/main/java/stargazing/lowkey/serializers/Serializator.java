package stargazing.lowkey.serializers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class Serializator {
    private static final String CLASS_FIELD_NOT_WANTED_UUID = "serialVersionUID";
    private static final String CLASS_FIELD_NOT_WANTED_CHANGE = "$change";

    private Object object;

    public Serializator(Object object) {
        this.object = object;
    }

    public JSONObject serialize() {
        JSONObject serializedObject = new JSONObject();

        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                Log.e("FIELD", field.getName());
                if(isFieldNameValid(field.getName()))
                    serializedObject.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                Log.e("serialize", e.getMessage());
            } catch (JSONException e) {
                Log.e("serialize", e.getMessage());
            }
        }

        return serializedObject;
    }

    private boolean isFieldNameValid(String name) {
        if(name.equals(CLASS_FIELD_NOT_WANTED_UUID))
            return false;

        if(name.equals(CLASS_FIELD_NOT_WANTED_CHANGE))
            return false;

        return true;
    }
}
