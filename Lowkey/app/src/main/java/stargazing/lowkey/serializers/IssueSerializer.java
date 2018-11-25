package stargazing.lowkey.serializers;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import stargazing.lowkey.models.IssueGetModel;
import stargazing.lowkey.models.IssueModel;

public class IssueSerializer {
    private JSONObject issuesSerialized;
    private String issueStringSerialized;
    private IssueGetModel issueGetModel;

    public IssueSerializer(IssueModel issueModel) {
        Gson gson = new Gson();
        String issuesStringSerialized = gson.toJson(issueModel);
        try {
            issuesSerialized = new JSONObject(issuesStringSerialized);
        } catch (JSONException e) {
            Log.e("IssueSerializer", e.getMessage());
        }
    }

    public IssueSerializer(IssueGetModel issueGetModel) {
        Gson gson = new Gson();
        issueStringSerialized = gson.toJson(issueGetModel);
    }

    public IssueSerializer(String issueGetModelStringSerialized) {
        Gson gson = new Gson();
        issueGetModel = gson.fromJson(issueGetModelStringSerialized, IssueGetModel.class);
    }

    public JSONObject getIssuesSerialized() {
        return issuesSerialized;
    }

    public String getIssueStringSerialized() {
        return issueStringSerialized;
    }

    public IssueGetModel getIssueGetModel() {
        return issueGetModel;
    }
}
