package stargazing.lowkey;

import android.app.Application;

import stargazing.lowkey.api.wrapper.RequestQueueSingleton;
import stargazing.lowkey.managers.UserManager;
import stargazing.lowkey.models.LoginModel;
import stargazing.lowkey.models.RegisterModel;
import stargazing.lowkey.models.UserModel;

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

        currentUserManager = new UserManager("p.e.iusztin@gmail.com");
    }

}
