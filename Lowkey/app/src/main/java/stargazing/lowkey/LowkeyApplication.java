package stargazing.lowkey;

import android.app.Application;

import stargazing.lowkey.api.wrapper.RequestQueueSingleton;

public class LowkeyApplication extends Application {

    public static LowkeyApplication instance;
    public static RequestQueueSingleton requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        requestQueue = RequestQueueSingleton.getInstance(this);


    }

}
