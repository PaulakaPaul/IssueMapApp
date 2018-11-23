package stargazing.lowkey.managers;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import stargazing.lowkey.api.views.UserView;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.models.LoginModel;
import stargazing.lowkey.models.RegisterModel;
import stargazing.lowkey.models.UserModel;
import stargazing.lowkey.serializers.UserSerializer;
import stargazing.lowkey.utils.SpUtils;

public class UserManager {
    public static final String RESPONSE_DATA_KEY = "Data";
    public static final String RESPONSE_TOKEN_KEY = "access_token";

    public static final String SHARED_PREF_EMAIL_KEY = "email";
    public static final String SHARED_PREF_PASS_KEY = "password";
    public static final String SHARED_PREF_EMPTY_VALUE = "";

    private UserView userView;

    private UserModel userModel;
    private String email;

    private String token;

    public UserManager(String email) {
        this.email = email;
        this.userView = new UserView();
    }

    public UserManager(UserModel userModel, String email) {
        this(email);
        this.userModel = userModel;
    }

    public void IsAuthorized(OnSuccessHandler onSuccessHandler) {
        OnSuccessHandler handleUserModel = getOnSuccessHandlerForUserModelRequest(onSuccessHandler);
        userView.getIsAuthorized(email, handleUserModel);
    }

    public boolean hasChachedCredentials() {
        SpUtils spUtils = new SpUtils();
        String email = spUtils.loadString(SHARED_PREF_EMAIL_KEY);
        String password = spUtils.loadString(SHARED_PREF_PASS_KEY);

        return !email.equals(SHARED_PREF_EMPTY_VALUE) &&
                !password.equals(SHARED_PREF_EMPTY_VALUE);
    }

    public void getUserModel(OnSuccessHandler onSuccessHandler) {
        OnSuccessHandler handleUserModel = getOnSuccessHandlerForUserModelRequest(onSuccessHandler);
        userView.getUserByEmail(email, handleUserModel);
    }

    public void postRegisterUser(RegisterModel registerModel, OnSuccessHandler response) {
        userView.postRegisterUser(registerModel, response);
    }

    public void postLoginUser(final LoginModel loginModel, final OnSuccessHandler onSuccessHandler) {
        OnSuccessHandler handleLoginSuccess = new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {
                cacheCredentials(loginModel.getEmail(), loginModel.getPassword());
                token = getTokenFromResponse(response);

                if(onSuccessHandler!=null)
                    onSuccessHandler.handle(response);
            }
        };

        userView.postLoginUser(loginModel, handleLoginSuccess);
    }

    public void signOut() {
        userView = null;
        userModel = null;
        email = null;
        token = null;

        SpUtils spUtils = new SpUtils();
        spUtils.save(SHARED_PREF_EMAIL_KEY, SHARED_PREF_EMPTY_VALUE);
        spUtils.save(SHARED_PREF_PASS_KEY, SHARED_PREF_EMPTY_VALUE);
    }

    private void cacheCredentials(String email, String password) {
        SpUtils spUtils = new SpUtils();
        spUtils.save(SHARED_PREF_EMAIL_KEY, email);
        spUtils.save(SHARED_PREF_PASS_KEY, password);
    }

    private String getTokenFromResponse(JSONObject response) {
        try {
            return response.getString(RESPONSE_TOKEN_KEY);
        } catch (JSONException e) {
            Log.e("getTokenFromResponse", e.getMessage());
        }

        return null;
    }

    private OnSuccessHandler getOnSuccessHandlerForUserModelRequest(final OnSuccessHandler onSuccessHandler) {
         return new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {
                // Populate userModel
                if (!response.equals(RequestWrapper.FAIL_JSON_RESPONSE_VALUE)) {
                    userModel = getUserModelFromResponse(response);
                }

                if (onSuccessHandler != null)
                    onSuccessHandler.handle(response);
            }
        };
    }

    private UserModel getUserModelFromResponse(JSONObject response) {
        try {
            JSONObject dataResponse = response.getJSONObject(RESPONSE_DATA_KEY);
            UserSerializer serializer = new UserSerializer(dataResponse);

            return serializer.getUserModel();
        } catch (JSONException e) {
            Log.e("getUserModelResponse", e.getMessage());
        }

        return null;
    }

    public String getToken() {
        return token;
    }
}
