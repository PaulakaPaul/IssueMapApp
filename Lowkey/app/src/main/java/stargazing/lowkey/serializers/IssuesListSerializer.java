package stargazing.lowkey.serializers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.List;

import stargazing.lowkey.models.IssueGetModel;

public class IssuesListSerializer {
    private List<IssueGetModel> issuesModel;

    public IssuesListSerializer(JSONArray issuesSerialized) {
        Gson gson = new Gson();
        String issuesStringSerialized = issuesSerialized.toString();
        issuesModel = gson.fromJson(issuesStringSerialized,
                new TypeToken<List<IssueGetModel>>(){}.getType());
    }

    public List<IssueGetModel> getIssues() {
        return issuesModel;
    }
}
