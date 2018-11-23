package stargazing.lowkey.models;

public class LoginModel {
    private String username;
    private String password;
    private String grant_type = "password";

    public LoginModel(String email, String password) {
        this.username = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGrantType() {
        return grant_type;
    }
}
