package stargazing.lowkey.api.wrapper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueSingleton {

    private static RequestQueueSingleton mInstance;
    private RequestQueue mRequestQueue;
    private Context mCtx;

    private RequestQueueSingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized RequestQueueSingleton getInstance(Context context) {

        if (mInstance == null) {

            if(context == null)
                throw new RuntimeException("Context is null");

            mInstance = new RequestQueueSingleton(context);
        }

        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

        }

        return mRequestQueue;
    }

    public void cancelRequests(String tag) {
        getRequestQueue().cancelAll(tag);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
