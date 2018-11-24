package stargazing.lowkey.api.wrapper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import stargazing.lowkey.LowkeyApplication;

public abstract class RequestItecWrapper extends RequestWrapper {
    private static final String ITEC_API_BASE_URL = "http://itec-api.deventure.co";

    public RequestItecWrapper(String tag) {
        super(ITEC_API_BASE_URL, tag);
    }

    public static Map<String, String> getTokenHeader() {
        Map<String, String> header = new HashMap<>();
        if(LowkeyApplication.currentUserManager.getToken() != null)
            header.put("Authorization", "Bearer " + LowkeyApplication.currentUserManager.getToken());

        return header;
    }

    @Override
    public void get(String relativeUrl, Map<?, ?> queryParams, JSONObject jsonObject, OnSuccessHandler onSuccessHandler) {
        super.get(relativeUrl, queryParams, getTokenHeader(), jsonObject, onSuccessHandler);
    }

    @Override
    public void post(String relativeUrl, Map<?, ?> queryParams, JSONObject jsonObject, OnSuccessHandler onSuccessHandler) {
        super.post(relativeUrl, queryParams, getTokenHeader(), jsonObject, onSuccessHandler);
    }
}
