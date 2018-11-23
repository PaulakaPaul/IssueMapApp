package stargazing.lowkey.serializers;

import com.google.gson.Gson;

import org.json.JSONObject;

import stargazing.lowkey.models.UserModel;

public class UserSerializer {
    private UserModel userModel;

    public UserSerializer(JSONObject userModelSerialized) {
        String stringUserSerialized = userModelSerialized.toString();
        Gson gson = new Gson();

        userModel = gson.fromJson(stringUserSerialized, UserModel.class);
    }

    public UserModel getUserModel() {
        return userModel;
    }
}
