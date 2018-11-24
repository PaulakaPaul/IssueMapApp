package stargazing.lowkey.managers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import stargazing.lowkey.api.views.IssuesView;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.OnSuccessListHandler;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.models.IssueGetModel;
import stargazing.lowkey.models.IssueModel;
import stargazing.lowkey.serializers.IssueSerializer;
import stargazing.lowkey.serializers.IssuesListSerializer;

public class IssueManager {
    private static final String ID_RESPONSE_KEY = "Id";
    private static final String SUCCESS_RESPONSE_KEY = "Success";

    private List<IssueGetModel> issues;
    private IssuesView issuesView = new IssuesView();

    public void getAll(final OnSuccessListHandler onSuccessListHandler) {
        OnSuccessListHandler populateIssuesHandler = new OnSuccessListHandler() {
            @Override
            public void handle(JSONArray response) {
                IssuesListSerializer issuesListSerializer = new IssuesListSerializer(response);
                issues = issuesListSerializer.getIssues();

                if(onSuccessListHandler != null && issues != null) {
                    onSuccessListHandler.handle(response);
                } else if(onSuccessListHandler != null)
                    onSuccessListHandler.handle(RequestWrapper.FAIL_JSON_LIST_RESPONSE_VALUE);
            }
        };

        issuesView.getAll(populateIssuesHandler);
    }

    public void createIssue(IssueModel issueModel, final OnSuccessHandler onSuccessHandler) {
        IssueSerializer issueSerializer = new IssueSerializer(issueModel);
        JSONObject body = issueSerializer.getIssuesSerialized();

        OnSuccessHandler validationHandler = new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {

                if(onSuccessHandler != null)
                    try {
                        response.get(ID_RESPONSE_KEY);
                        onSuccessHandler.handle(response);
                    } catch (JSONException e) {
                        onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
                    }
            }
        };

        issuesView.create(body, validationHandler);
    }

    public void updateIssues(IssueModel issueModel, final OnSuccessHandler onSuccessHandler) {
        IssueSerializer issueSerializer = new IssueSerializer(issueModel);
        JSONObject body = issueSerializer.getIssuesSerialized();

        OnSuccessHandler validationHandler = new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {

                if(onSuccessHandler != null)
                    try {
                        response.get(ID_RESPONSE_KEY);
                        onSuccessHandler.handle(response);
                    } catch (JSONException e) {
                        onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
                    }
            }
        };

        issuesView.update(body, validationHandler);
    }

    public void voteUp(UUID issueId, OnSuccessHandler onSuccessHandler) {
        issuesView.voteUp(issueId, getSuccessValidationHandler(onSuccessHandler));
    }

    public void voteDown(UUID issueId, OnSuccessHandler onSuccessHandler) {
        issuesView.voteDown(issueId, getSuccessValidationHandler(onSuccessHandler));
    }

    public ArrayList<IssueGetModel> getIssues() {
        return (ArrayList<IssueGetModel>) issues;
    }

    private OnSuccessHandler getSuccessValidationHandler(final OnSuccessHandler onSuccessHandler) {
        return new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {
                if(onSuccessHandler != null)
                    try {
                        response.get(SUCCESS_RESPONSE_KEY);
                        onSuccessHandler.handle(response);
                    } catch (JSONException e) {
                        onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
                    }
            }
        };
    }
}
