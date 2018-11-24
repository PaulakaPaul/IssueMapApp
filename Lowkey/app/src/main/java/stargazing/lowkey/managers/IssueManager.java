package stargazing.lowkey.managers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import stargazing.lowkey.api.views.IssuesView;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.OnSuccessListHandler;
import stargazing.lowkey.models.IssueGetModel;
import stargazing.lowkey.models.IssueModel;
import stargazing.lowkey.serializers.IssueSerializer;
import stargazing.lowkey.serializers.IssuesListSerializer;

public class IssueManager {

    private List<IssueGetModel> issues;
    private IssuesView issuesView = new IssuesView();

    public void getAll(final OnSuccessListHandler onSuccessListHandler) {
        OnSuccessListHandler populateIssuesHandler = new OnSuccessListHandler() {
            @Override
            public void handle(JSONArray response) {
                IssuesListSerializer issuesListSerializer = new IssuesListSerializer(response);
                issues = issuesListSerializer.getIssues();

                if(onSuccessListHandler != null)
                    onSuccessListHandler.handle(response);
            }
        };

        issuesView.getAll(populateIssuesHandler);
    }

    public void createIssue(IssueModel issueModel, OnSuccessHandler onSuccessHandler) {
        IssueSerializer issueSerializer = new IssueSerializer(issueModel);
        JSONObject body = issueSerializer.getIssuesSerialized();

        issuesView.create(body, onSuccessHandler);
    }

    public void updateIssues(IssueModel issueModel, OnSuccessHandler onSuccessHandler) {
        IssueSerializer issueSerializer = new IssueSerializer(issueModel);
        JSONObject body = issueSerializer.getIssuesSerialized();

        issuesView.update(body, onSuccessHandler);
    }

    public void voteUp(UUID issueId, OnSuccessHandler onSuccessHandler) {
        issuesView.voteUp(issueId, onSuccessHandler);
    }

    public void voteDown(UUID issueId, OnSuccessHandler onSuccessHandler) {
        issuesView.voteDown(issueId, onSuccessHandler);
    }

    public ArrayList<IssueGetModel> getIssues() {
        return (ArrayList<IssueGetModel>) issues;
    }
}
