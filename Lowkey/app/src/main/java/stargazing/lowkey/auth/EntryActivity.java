package stargazing.lowkey.auth;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;

import stargazing.lowkey.LowkeyApplication;
import stargazing.lowkey.R;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.auth.login.LoginActivity;
import stargazing.lowkey.auth.register.RegisterActivity1EP;
import stargazing.lowkey.main.fragments.MainActivity;

public class EntryActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ConstraintLayout constraintLayout;

    private TextView login;
    private TextView register;
    private TextView facebooklogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        StatusBarUtil.setTransparent(this);
        initUI();

        switchView(true);
        goToMainIfLoggedIn();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0, 0);
                Intent intent = new Intent(getApplicationContext(), RegisterActivity1EP.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0, 0);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        facebooklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0, 0);
                LowkeyApplication.isAnnonymous = true;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


    }
    @Override
    public void onBackPressed() {

    }

    private void initUI() {
        progressBar = findViewById(R.id.progressBar);
        constraintLayout = findViewById(R.id.constraintLayout);

        login = findViewById(R.id.login);
        register = findViewById(R.id.signup);
        facebooklogin = findViewById(R.id.facebooklogin);
    }

    private void switchView(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        constraintLayout.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    private void goToMainIfLoggedIn() {
        LowkeyApplication.currentUserManager.isUserLoggedIn(new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {
                if(!response.equals(RequestWrapper.FAIL_JSON_RESPONSE_VALUE)) {
                    Intent intent = new Intent(EntryActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    switchView(false);
                }
            }
        });
    }
}
