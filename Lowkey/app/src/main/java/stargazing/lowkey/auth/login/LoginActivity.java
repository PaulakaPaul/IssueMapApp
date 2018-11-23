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

import stargazing.lowkey.R;
import stargazing.lowkey.auth.register.RegisterActivity2FL;
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
                overridePendingTransition(0, 0);

            }
        });
    }

    private boolean readyToGo(String email, String password){
        if(AttributesValidator.isEmailValid(email) && AttributesValidator.isPasswordValid(password))
            return true;
        return false;
    }
}
