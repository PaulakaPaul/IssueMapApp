package stargazing.lowkey.api.wrapper;

public class AccountWrapper extends RequestItecWrapper {
    private static final String TAG = "Account";

    public static final String GET_USER_BY_EMAIL_RELATIVE_URL = "/api/Account/GetUserByEmail?email=pauliusztin%40yahoo.co";

    public AccountWrapper() {
        super(TAG);
    }

    public void getUserByEmail() {
        super.get(GET_USER_BY_EMAIL_RELATIVE_URL, null);
    }
}
