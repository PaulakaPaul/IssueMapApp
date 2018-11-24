package stargazing.lowkey;

import android.app.Application;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestQueueSingleton;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.managers.UserManager;
import stargazing.lowkey.models.IssueModel;
import stargazing.lowkey.models.LoginModel;
import stargazing.lowkey.models.RegisterModel;

public class LowkeyApplication extends Application {

    public static LowkeyApplication instance;
    public static RequestQueueSingleton requestQueue;

    public static UserManager currentUserManager;

    public static RegisterModel registerModel = new RegisterModel("Iusztin Paul", "p.e.iusztin.d@gmail.com",
            21.21D, 45.44D, 3, 22, 0, "ceaispus");

    public static LoginModel loginModel = new LoginModel("p.e.iusztin@gmail.com", "ceaispus");

    public static IssueModel issueModel = new IssueModel(
            "Issue Test Updated By Stargazing", "This is the description for issue test", 45.748871, 21.208679,
            UUID.fromString("adebef04-b1ee-462c-9da2-01182652e45d"),
            new ArrayList<String>());

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        requestQueue = RequestQueueSingleton.getInstance(this);

        currentUserManager = getCurrentUserManager();
    }

    public void logout() {
        if (currentUserManager != null) {
            currentUserManager.logout();
        }

        currentUserManager = new UserManager();
    }

    private UserManager getCurrentUserManager() {
        if (UserManager.hasCachedCredentials()) {
            String currentUserEmail = UserManager.getCachedEmail();
            return new UserManager(currentUserEmail);
        }

        return new UserManager();
    }

}
