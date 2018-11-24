package stargazing.lowkey.serializers;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import stargazing.lowkey.models.IssueModel;

public class IssueSerializer {
    JSONObject issuesSerialized;

    public IssueSerializer(IssueModel issueModel) {
        Gson gson = new Gson();
        String issuesStringSerialized = gson.toJson(issueModel);
        try {
            issuesSerialized = new JSONObject(issuesStringSerialized);
        } catch (JSONException e) {
            Log.e("IssueSerializer", e.getMessage());
        }
    }

    public JSONObject getIssuesSerialized() {
        return issuesSerialized;
    }
}
