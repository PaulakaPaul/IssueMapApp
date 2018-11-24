package stargazing.lowkey.api.views;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.OnSuccessListHandler;
import stargazing.lowkey.api.wrapper.RequestItecWrapper;

public class IssuesView extends RequestItecWrapper {
    private static final String TAG = "Issues";

    private static final int VOTE_UP = 0;
    private static final int VOTE_DOWN = 1;

    private static final String ISSUES_ID_KEY = "IssueId";
    private static final String VOTE_TYPE_KEY = "VoteType";

    private static final String CREATE_URL = "/api/Issue/Create";
    private static final String UPDATE_URL = "/api/Issue/Update";
    private static final String VOTE_URL = "/api/Issue/Vote";
    private static final String DELETE_ID_PICTURE = "/api/Issue/DeletePicture/";

    private static final String GET_ALL_URL = "/api/Issue/GetAll";
    private static final String GET_ID_URL = "/api/Issue/Get"; //TODO: with id


    public IssuesView() {
        super(TAG);
    }

    public void getAll(OnSuccessListHandler onSuccessListHandler) {
        super.get(GET_ALL_URL, null,
                null, onSuccessListHandler);
    }

    public void create(JSONObject issue, OnSuccessHandler onSuccessHandler) {
        super.post(CREATE_URL, null,
                issue, onSuccessHandler);
    }

    public void update(JSONObject issue, OnSuccessHandler onSuccessHandler) {
        super.post(UPDATE_URL, null,
                issue, onSuccessHandler);
    }

    public void voteUp(UUID issueId, OnSuccessHandler onSuccessHandler) {
        vote(issueId, VOTE_UP, onSuccessHandler);
    }

    public void voteDown(UUID issueId, OnSuccessHandler onSuccessHandler) {
        vote(issueId, VOTE_DOWN, onSuccessHandler);
    }

    private void vote(UUID issueId, int voteType, OnSuccessHandler onSuccessHandler) {
        try {
            JSONObject body = new JSONObject();
            body.put(ISSUES_ID_KEY, issueId);
            body.put(VOTE_TYPE_KEY, voteType);

        super.post(VOTE_URL, null,
                body, onSuccessHandler);

        } catch (JSONException e) {
            Log.e("voteType: " + voteType, e.getMessage());
        }
    }
}
