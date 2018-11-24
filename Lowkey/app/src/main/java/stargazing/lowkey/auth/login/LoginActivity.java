package stargazing.lowkey.auth.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;

import java.lang.reflect.Method;

import stargazing.lowkey.LowkeyApplication;
import stargazing.lowkey.R;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.auth.register.RegisterActivity2FL;
import stargazing.lowkey.main.fragments.MainActivity;
import stargazing.lowkey.models.LoginModel;
import stargazing.lowkey.utils.AttributesValidator;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button next;
    private ImageView back;

    private static String ERROR_BAD_EMAIL = "Email or password is not valid !";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTransparent(this);

        initUI();

        setOnClickListeners();
    }

    private void initUI() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back1);
    }

    private void setOnClickListeners() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0, 0);
                onBackPressed();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if(readyToGo(emailText, passwordText)) {
                    LoginModel loginModel = new LoginModel(emailText, passwordText);
                    LowkeyApplication.currentUserManager.postLoginUser(loginModel, new OnSuccessHandler() {
                        @Override
                        public void handle(JSONObject response) {
                            if(!response.equals(RequestWrapper.FAIL_JSON_RESPONSE_VALUE)) {
                                LowkeyApplication.currentUserManager.setEmail(emailText);

                                overridePendingTransition(0, 0);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                            } else {
                                Toast.makeText(LoginActivity.this,
                                        "Could not login.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean readyToGo(String email, String password){
        if(!AttributesValidator.isEmailValid(email)) {
            Toast.makeText(this, "Your email is invalid", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!AttributesValidator.isPasswordValid(password)) {
            Toast.makeText(this, "Your password is invalid", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
