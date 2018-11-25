package stargazing.lowkey.serializers;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import stargazing.lowkey.models.UpdateUserModel;

public class UpdateUserModelSerializer {
    private JSONObject updateUserModelSerialized;

    public UpdateUserModelSerializer(UpdateUserModel updateUserModel) {
        Gson gson = new Gson();
        try {
            String updateUserModelStringSerialized = gson.toJson(updateUserModel);
            updateUserModelSerialized = new JSONObject(updateUserModelStringSerialized);
        } catch (JSONException e) {
            Log.e("UpdateUseSerializer", e.getMessage());
        }
    }

    public JSONObject getUpdateUserModelSerialized() {
        return updateUserModelSerialized;
    }
}
