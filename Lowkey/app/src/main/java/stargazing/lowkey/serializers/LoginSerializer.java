package stargazing.lowkey.serializers;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import stargazing.lowkey.models.LoginModel;

public class LoginSerializer {
    private JSONObject loginSerializedModel;

    public LoginSerializer(LoginModel loginModel) {
        try {
            Gson gson = new Gson();
            String serializedJson = gson.toJson(loginModel);
            loginSerializedModel = new JSONObject(serializedJson);
        } catch (JSONException e) {
            Log.e("LoginSerializer", e.getMessage());
        }
    }

    public JSONObject getLoginSerializedModel() {
        return loginSerializedModel;
    }
}
