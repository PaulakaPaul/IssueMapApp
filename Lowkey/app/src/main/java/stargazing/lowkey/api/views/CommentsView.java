package stargazing.lowkey.api.views;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestItecWrapper;
import stargazing.lowkey.api.wrapper.RequestWrapper;

public class CommentsView extends RequestItecWrapper {
    private static final String TAG = "Comment";

    private static final String SUCCESS_RESPONSE_KEY = "Success";

    private static final String CREATE_URL = "/api/Comment/Create";
    private static final String EDIT_URL = "/api/Comment/Edit";
    private static final String DELETE_ID_URL = "/api/Comment/Delete";

    public CommentsView() {
        super(TAG);
    }

    public void create(JSONObject body, OnSuccessHandler onSuccessHandler) {
        super.post(CREATE_URL, null, body, onSuccessHandler);
    }

    public void edit(JSONObject body, OnSuccessHandler onSuccessHandler) {
        super.post(EDIT_URL, null, body, onSuccessHandler);
    }

    public void delete(UUID id, final OnSuccessHandler onSuccessHandler) {
        String url = DELETE_ID_URL + "/" + id.toString();
        OnSuccessHandler onDeleteSuccessHandler = new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {
                if(isRequestValid(response)) {
                    if (onSuccessHandler != null)
                        onSuccessHandler.handle(response);
                } else if(onSuccessHandler != null) {
                    onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
                }
            }
        };

        super.post(url, null, null, onDeleteSuccessHandler);
    }

    private boolean isRequestValid(JSONObject response) {
        try {
            return response.getBoolean(SUCCESS_RESPONSE_KEY);
        } catch (JSONException e) {
            Log.e("IsRequestValid", e.getMessage());
            return false;
        }
    }

}
