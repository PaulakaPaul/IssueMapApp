package stargazing.lowkey;

import android.app.Application;

import org.json.JSONObject;

import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestQueueSingleton;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.managers.UserManager;
import stargazing.lowkey.models.LoginModel;
import stargazing.lowkey.models.RegisterModel;

public class LowkeyApplication extends Application {

    public static LowkeyApplication instance;
    public static RequestQueueSingleton requestQueue;

    public static UserManager currentUserManager;

    public static RegisterModel registerModel = new RegisterModel("Iusztin Paul", "p.e.iusztin.d@gmail.com",
            21.21D, 45.44D, 3, 22, 0, "ceaispus");

    public static LoginModel loginModel = new LoginModel("p.e.iusztin@gmail.com", "ceaispus");

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        requestQueue = RequestQueueSingleton.getInstance(this);

        currentUserManager = getCurrentUserManager();
    }

    public void isUserLoggedIn(final OnSuccessHandler loggedInHandler) {
        currentUserManager.IsAuthorized(new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {
                if(!response.equals(RequestWrapper.FAIL_JSON_RESPONSE_VALUE))
                    if(UserManager.hasCachedCredentials())
                        if(loggedInHandler != null)
                            loggedInHandler.handle(response);
            }
        });
    }

    public void logout() {
        if(currentUserManager != null) {
            currentUserManager.logout();
        }

        currentUserManager = new UserManager();
    }

    private UserManager getCurrentUserManager() {
        if(UserManager.hasCachedCredentials()) {
            String currentUserEmail = UserManager.getCachedEmail();
            return new UserManager(currentUserEmail);
        }

        return new UserManager();
    }

}
