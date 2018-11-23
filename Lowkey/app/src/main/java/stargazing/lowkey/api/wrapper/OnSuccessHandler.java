package stargazing.lowkey.api.wrapper;

import org.json.JSONObject;

public interface OnSuccessHandler {
    void handle(JSONObject response);
}
