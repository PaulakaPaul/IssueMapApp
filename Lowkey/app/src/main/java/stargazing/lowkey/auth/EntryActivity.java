package stargazing.lowkey.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import stargazing.lowkey.R;

public class EntryActivity extends AppCompatActivity {

    private TextView login;
    private TextView register;
    private TextView facebooklogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        initUI();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0, 0);
                Intent intent = new Intent(getApplicationContext(), RegisterActivity1EP.class);
                startActivity(intent);

            }
        });


    }

    private void initUI() {
        login = findViewById(R.id.login);
        register = findViewById(R.id.signup);
        facebooklogin = findViewById(R.id.facebooklogin);
    }
}
