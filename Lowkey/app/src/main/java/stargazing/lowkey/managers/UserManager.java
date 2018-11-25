package stargazing.lowkey.managers;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import stargazing.lowkey.api.photos.PhotoNameTranslator;
import stargazing.lowkey.api.views.UserView;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.models.LoginModel;
import stargazing.lowkey.models.RegisterModel;
import stargazing.lowkey.models.UpdateUserModel;
import stargazing.lowkey.models.UserModel;
import stargazing.lowkey.serializers.UpdateUserModelSerializer;
import stargazing.lowkey.serializers.UserSerializer;
import stargazing.lowkey.utils.SpUtils;

public class UserManager {
    public static final String RESPONSE_DATA_KEY = "Data";
    public static final String RESPONSE_TOKEN_KEY = "access_token";
    public static final String RESPONSE_SUCCESSFUL_KEY = "Success";

    public static final String SHARED_PREF_EMAIL_KEY = "email";
    public static final String SHARED_PREF_PASS_KEY = "password";
    public static final String SHARED_PREF_EMPTY_VALUE = "";

    private UserView userView;

    private UserModel userModel;
    private String email;

    private String token;

    public UserManager() {
        this.userView = new UserView();
    }

    public UserManager(String email) {
        this();
        this.email = email;
    }

    public UserManager(UserModel userModel, String email) {
        this(email);
        this.userModel = userModel;
    }

    public void IsAuthorized(String token, OnSuccessHandler onSuccessHandler) {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);

        OnSuccessHandler handleUserModel = getOnSuccessHandlerForUserModelRequest(onSuccessHandler);
        userView.getIsAuthorized(email, header, handleUserModel);
    }

    public void isUserLoggedIn(final OnSuccessHandler loggedInHandler) {
        if (UserManager.hasCachedCredentials()) {
            this.email = UserManager.getCachedEmail();
            LoginModel loginModel = UserManager.getChangedLoginModel();

            postLoginUser(loginModel, new OnSuccessHandler() {
                @Override
                public void handle(JSONObject response) {
                    if (!response.equals(RequestWrapper.FAIL_JSON_RESPONSE_VALUE)) {
                        IsAuthorized(token, new OnSuccessHandler() {
                            @Override
                            public void handle(JSONObject response) {
                                if (loggedInHandler != null)
                                    loggedInHandler.handle(response);
                            }
                        });
                    }

                    if (loggedInHandler != null)
                        loggedInHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
                }
            });

        } else if (loggedInHandler != null) {
            loggedInHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
        }
    }

    public void updateUser(final UpdateUserModel userModel, final OnSuccessHandler onSuccessHandler) {
        UpdateUserModelSerializer userSerializer = new UpdateUserModelSerializer(userModel);
        JSONObject jsonObject = userSerializer.getUpdateUserModelSerialized();

        OnSuccessHandler validationHandler = new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {
                if (onSuccessHandler != null)
                    try {
                        if(response.getBoolean(RESPONSE_SUCCESSFUL_KEY))
                            onSuccessHandler.handle(response);
                         else
                             onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
                    } catch (JSONException e) {
                        onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
                    }
            }
        };

        userView.updateUser(jsonObject, validationHandler);
    }

    public static boolean hasCachedCredentials() {
        SpUtils spUtils = new SpUtils();
        String email = spUtils.loadString(SHARED_PREF_EMAIL_KEY);
        if (email == null)
            return false;

        String password = spUtils.loadString(SHARED_PREF_PASS_KEY);
        if (password == null)
            return false;

        return !email.equals(SHARED_PREF_EMPTY_VALUE) &&
                !password.equals(SHARED_PREF_EMPTY_VALUE);
    }

    public static String getCachedEmail() {
        SpUtils spUtils = new SpUtils();
        return spUtils.loadString(SHARED_PREF_EMAIL_KEY);
    }

    public static String getCachedPassword() {
        SpUtils spUtils = new SpUtils();
        return spUtils.loadString(SHARED_PREF_PASS_KEY);
    }

    public static LoginModel getChangedLoginModel() {
        if (!hasCachedCredentials())
            return null;

        return new LoginModel(getCachedEmail(), getCachedPassword());
    }

    public void requestUserModel(OnSuccessHandler onSuccessHandler) {
        OnSuccessHandler handleUserModel = getOnSuccessHandlerForUserModelRequest(onSuccessHandler);
        userView.getUserByEmail(email, handleUserModel);
    }

    public void postRegisterUser(RegisterModel registerModel, final OnSuccessHandler onSuccessHandler) {
        OnSuccessHandler handleOnRegisterSuccess = new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {
                if (isSuccessful(response)) {
                    if (onSuccessHandler != null)
                        onSuccessHandler.handle(response);
                } else {
                    if (onSuccessHandler != null)
                        onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
                }
            }
        };

        userView.postRegisterUser(registerModel, handleOnRegisterSuccess);
    }

    public void postLoginUser(final LoginModel loginModel,
                              final OnSuccessHandler onSuccessHandler) {
        this.email = loginModel.getEmail();

        OnSuccessHandler handleLoginSuccess = new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {
                token = getTokenFromResponse(response);

                if (token != null) {
                    requestUserModel(new OnSuccessHandler() {
                        @Override
                        public void handle(JSONObject response) {
                            if (!response.equals(RequestWrapper.FAIL_JSON_RESPONSE_VALUE)) {
                                cacheCredentials(loginModel.getEmail(), loginModel.getPassword());
                                if (onSuccessHandler != null)
                                    onSuccessHandler.handle(response);
                            } else if (onSuccessHandler != null) {
                                onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
                            }
                        }
                    });

                } else if (onSuccessHandler != null)
                    onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
            }
        };

        userView.postLoginUser(loginModel, handleLoginSuccess);
    }

    public void logout() {
        userView = null;
        userModel = null;
        email = null;
        token = null;

        SpUtils spUtils = new SpUtils();
        spUtils.save(SHARED_PREF_EMAIL_KEY, SHARED_PREF_EMPTY_VALUE);
        spUtils.save(SHARED_PREF_PASS_KEY, SHARED_PREF_EMPTY_VALUE);
    }

    private boolean isSuccessful(JSONObject jsonObject) {
        try {
            return jsonObject.getBoolean(RESPONSE_SUCCESSFUL_KEY);
        } catch (JSONException e) {
            return false;
        }
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
                if (isSuccessful(response)) {
                    userModel = getUserModelFromResponse(response);

                    if (onSuccessHandler != null)
                        onSuccessHandler.handle(response);

                } else if (onSuccessHandler != null) {
                    onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);
                }
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

    public UserModel getUserModel() {
        return this.userModel;
    }

    public String getProfilePictureName() {
        return PhotoNameTranslator.getPhotoNameFromEmail(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
