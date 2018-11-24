package stargazing.lowkey.serializers;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import stargazing.lowkey.models.CommentModel;

public class CommentSerializer {
    private JSONObject commentSerialized;

    public CommentSerializer(CommentModel commentModel) {
        try {
            Gson gson = new Gson();
            String commentStringSerialized = gson.toJson(commentModel);
            commentSerialized = new JSONObject(commentStringSerialized);
        } catch (JSONException e) {
            Log.e("CommentSerializer", e.getMessage());
        }
    }

    public JSONObject getCommentSerialized() {
        return commentSerialized;
    }
}
