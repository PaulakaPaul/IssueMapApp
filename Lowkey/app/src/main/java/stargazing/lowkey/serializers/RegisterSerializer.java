package stargazing.lowkey.serializers;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import stargazing.lowkey.models.RegisterModel;

public class RegisterSerializer {
    private JSONObject serializedRegisterModel;

    public RegisterSerializer(RegisterModel registerModel) {
        Gson gsonSerializer = new Gson();
        try {
            this.serializedRegisterModel = new JSONObject(gsonSerializer.toJson(registerModel));
        } catch (JSONException e) {
            Log.e("RegisterSerializer", e.getMessage());
        }
    }

    public JSONObject getSerializedRegisterModel() {
        return serializedRegisterModel;
    }
}
