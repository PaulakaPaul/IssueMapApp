package stargazing.lowkey.api.views;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestItecWrapper;
import stargazing.lowkey.models.LoginModel;
import stargazing.lowkey.models.RegisterModel;
import stargazing.lowkey.serializers.LoginSerializer;
import stargazing.lowkey.serializers.RegisterSerializer;

public class UserView extends RequestItecWrapper {
    private static final String TAG = "Account";

    private static final String USER_BY_EMAIL_RELATIVE_URL = "/api/Account/GetUserByEmail";
    private static final String IS_AUTHORIZED_URL = "/api/Account/IsAuthorized";
    private static final String REGISTER_URL = "/api/Account/Register";
    private static final String TOKEN_URL = "/api/Token";

    public static final String EMAIL_QUERY_PARAM_KEY = "email";

    public UserView() {
        super(TAG);
    }

    public void getUserByEmail(String email, OnSuccessHandler response) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(EMAIL_QUERY_PARAM_KEY, email);

        super.get(USER_BY_EMAIL_RELATIVE_URL, queryParams, null, response);
    }

    public void getIsAuthorized(String email, OnSuccessHandler response) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(EMAIL_QUERY_PARAM_KEY, email);

        super.get(IS_AUTHORIZED_URL, queryParams, null, response);
    }

    public void postRegisterUser(RegisterModel registerModel, OnSuccessHandler response) {
        RegisterSerializer serializer = new RegisterSerializer(registerModel);
        JSONObject registerModelSerialized = serializer.getSerializedRegisterModel();

        super.post(REGISTER_URL, null, registerModelSerialized, response);
    }

    public void postLoginUser(LoginModel loginModel, OnSuccessHandler response) {
        LoginSerializer serializer = new LoginSerializer(loginModel);
        JSONObject loginModelSerialized = serializer.getLoginSerializedModel();

        super.post(TOKEN_URL, null, loginModelSerialized, response);
    }
}
