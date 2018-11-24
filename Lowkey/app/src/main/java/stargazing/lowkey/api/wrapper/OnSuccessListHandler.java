package stargazing.lowkey.api.wrapper;

import org.json.JSONArray;

public interface OnSuccessListHandler {
    void handle(JSONArray response);
}
