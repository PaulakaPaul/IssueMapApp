package stargazing.lowkey;

import android.app.Application;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestQueueSingleton;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.managers.UserManager;
import stargazing.lowkey.models.CommentModel;
import stargazing.lowkey.models.IssueGetModel;
import stargazing.lowkey.models.IssueModel;
import stargazing.lowkey.models.LoginModel;
import stargazing.lowkey.models.RegisterModel;
import stargazing.lowkey.models.UpdateUserModel;

public class LowkeyApplication extends Application {

    public static LowkeyApplication instance;
    public static RequestQueueSingleton requestQueue;

    public static ArrayList<IssueGetModel> staticIssues = null;

    public static UserManager currentUserManager;
    public static boolean isAnnonymous = false;

    public static RegisterModel registerModel = new RegisterModel("Iusztin Paul", "p.e.iusztin.d@gmail.com",
            21.21D, 45.44D, 3, 22, 0, "ceaispus");

    public static LoginModel loginModel = new LoginModel("p.e.iusztin@gmail.com", "ceaispus");

    public static IssueModel issueModel = new IssueModel(UUID.fromString("dd063084-7ec1-4bc9-be87-02cb99835d3d"),
            "Issue Test Updated By Stargazing 222", "This is the description for issue test", 45.748871, 21.208679,
            UUID.fromString("adebef04-b1ee-462c-9da2-01182652e45d"),
            new ArrayList<String>());

    public static CommentModel commentModel = new CommentModel(
            UUID.fromString("3605886a-93f9-4702-a381-10f0860bcb85"),
            "Content from stargazing edited", UUID.fromString("219c01f9-d6a8-4412-99d8-5e286866dcb4"));

    public static UpdateUserModel updateUserModel = new UpdateUserModel(UUID.fromString("219c01f9-d6a8-4412-99d8-5e286866dcb4"),
            "Lowkey Updated", 30, 10, 1, 10, 10, "new photo");

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

        LowkeyApplication.isAnnonymous = false;
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
