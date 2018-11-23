package stargazing.lowkey.api.wrapper;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import stargazing.lowkey.LowkeyApplication;

public abstract class RequestWrapper {
    public static final JSONObject FAIL_JSON_RESPONSE_VALUE = new JSONObject();

    private String baseUrl;
    private String tag;

    public RequestWrapper(String baseUrl, String tag) {
        this.baseUrl = baseUrl;
        this.tag = tag;
    }

    public void get(String relativeUrl,
                    Map<?, ?> queryParams,
                    JSONObject jsonObject,
                    OnSuccessHandler onSuccessHandler) {
        request(Request.Method.GET, relativeUrl, queryParams, jsonObject, onSuccessHandler);
    }

    public void post(String relativeUrl,
                     Map<?, ?> queryParams,
                     JSONObject jsonObject,
                     OnSuccessHandler onSuccessHandler) {
        request(Request.Method.POST, relativeUrl, queryParams, jsonObject, onSuccessHandler);
    }

    public void patch(String relativeUrl,
                      Map<?, ?> queryParams,
                      JSONObject jsonObject,
                      OnSuccessHandler onSuccessHandler) {
        request(Request.Method.PATCH, relativeUrl, queryParams, jsonObject, onSuccessHandler);
    }

    public void delete(String relativeUrl,
                       Map<?, ?> queryParams,
                       JSONObject jsonObject,
                       OnSuccessHandler onSuccessHandler) {
        request(Request.Method.DELETE, relativeUrl, queryParams, jsonObject, onSuccessHandler);
    }

    public void cancelRequests() {
        LowkeyApplication.requestQueue.cancelRequests(this.tag);
    }

    private void request(final int mode, final String relativeUrl,
                         final Map<?, ?> queryParams,
                         final JSONObject body,
                         final OnSuccessHandler onSuccessHandler) {

        String url = getFullUrl(relativeUrl, queryParams);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(mode, url, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("jsonRequestSuccess", response.toString());
                        if(onSuccessHandler != null)
                            onSuccessHandler.handle(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            Log.e("JSONErrorResponse", error.getMessage());
                        } catch (NullPointerException e) {
                            Log.e("JSONErrorResponse", "Volley failed");
                        }

                        // If the request fails a stringRequest should do the trick.
                        stringRequest(mode, relativeUrl, queryParams, body, onSuccessHandler);
                    }
                })
            {
                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };


        jsonObjectRequest.setTag(this.tag);
        LowkeyApplication.requestQueue.addToRequestQueue(jsonObjectRequest);
    }

    private void stringRequest(int mode, final String relativeUrl,
                               Map<?, ?> queryParams,
                               final JSONObject body,
                               final OnSuccessHandler onSuccessHandler) {

        String url = getFullUrl(relativeUrl, queryParams);
        StringRequest stringRequest = new StringRequest(mode, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("stringRequestSuccess", response);
                try {
                    if(onSuccessHandler != null) {
                        JSONObject jsonReponse = new JSONObject(response);
                        onSuccessHandler.handle(jsonReponse);
                    }
                } catch (JSONException e) {
                    Log.e("stringRequestSuccess", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("stringErrorResponse", error.getMessage());
                } catch (NullPointerException e) {
                    Log.e("stringErrorResponse", "Volley failed");
                }

                if(onSuccessHandler!=null)
                    onSuccessHandler.handle(FAIL_JSON_RESPONSE_VALUE);
                }
            })
        {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, Object> mapBody = jsonToMap(body);
                return getQueryStrings(mapBody).getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };

        stringRequest.setTag(this.tag);
        LowkeyApplication.requestQueue.addToRequestQueue(stringRequest);
    }

    private HashMap<String, Object> jsonToMap(JSONObject jsonObject) {
        HashMap<String, Object> items = new HashMap<>();

        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();

            try {
                Object value = jsonObject.get(key);
                items.put(key, value);
            } catch (JSONException e) {
                Log.e("jsonToMap", e.getMessage());
            }
        }

        return items;
    }

    private String getFullUrl(String relativeUrl) {
        return getFullUrl(relativeUrl, null);
    }

    private String getFullUrl(String relativeUrl, Map<?, ?> queryParameters) {
        String absoluteURL = baseUrl + relativeUrl;

        if(queryParameters == null)
            return absoluteURL;

        return absoluteURL + "?" + getQueryStrings(queryParameters);
    }

    private String getQueryStrings(Map<?, ?> queryParameters) {
        if(queryParameters == null)
            return "";

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<?, ?> entry : queryParameters.entrySet()) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(String.format("%s=%s",
                        urlEncodeUTF8(entry.getKey().toString()),
                        urlEncodeUTF8(entry.getValue().toString())
                ));
            }
        return sb.toString();
    }

    private static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
