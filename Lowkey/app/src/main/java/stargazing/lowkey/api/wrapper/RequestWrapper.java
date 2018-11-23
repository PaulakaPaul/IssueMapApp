package stargazing.lowkey.api.wrapper;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import stargazing.lowkey.LowkeyApplication;

public abstract class RequestWrapper {
    private String baseUrl;
    private String tag;

    public RequestWrapper(String baseUrl, String tag) {
        this.baseUrl = baseUrl;
        this.tag = tag;
    }

    public void get(String relativeUrl, JSONObject jsonObject) {
        request(Request.Method.GET, relativeUrl, jsonObject);
    }

    public void post(String relativeUrl, JSONObject jsonObject) {
        request(Request.Method.POST, relativeUrl, jsonObject);
    }

    public void patch(String relativeUrl, JSONObject jsonObject) {
        request(Request.Method.PATCH, relativeUrl, jsonObject);
    }

    public void delete(String relativeUrl, JSONObject jsonObject) {
        request(Request.Method.DELETE, relativeUrl, jsonObject);
    }

    private void request(int mode, final String relativeUrl, JSONObject jsonRequest) {
        String url = getFullUrl(relativeUrl);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(mode, url, jsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("SUCCESS", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FAIL", error.toString());
                    }
                }
        );

        jsonObjectRequest.setTag(this.tag);
        LowkeyApplication.requestQueue.addToRequestQueue(jsonObjectRequest);
    }

    public void cancelRequests() {
        LowkeyApplication.requestQueue.cancelRequests(this.tag);
    }

    private String getFullUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }
}
