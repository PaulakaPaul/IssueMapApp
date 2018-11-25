package stargazing.lowkey.serializers;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import stargazing.lowkey.models.UserModel;

public class UserSerializer {
    private UserModel userModel;
    private JSONObject userModelSerialized;

    public UserSerializer(JSONObject userModelSerialized) {
        String stringUserSerialized = userModelSerialized.toString();
        Gson gson = new Gson();

        userModel = gson.fromJson(stringUserSerialized, UserModel.class);
    }

    public UserSerializer(UserModel userModel) {
        Gson gson = new Gson();
        String userStringSerialized = gson.toJson(userModel);
        try {
            userModelSerialized = new JSONObject(userStringSerialized);
        } catch (JSONException e) {
            Log.e("UserSerializer", e.getMessage());
        }
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public JSONObject getUserModelSerialized() {
        return userModelSerialized;
    }
}
