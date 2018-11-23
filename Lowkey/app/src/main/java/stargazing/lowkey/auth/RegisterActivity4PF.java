package stargazing.lowkey.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import stargazing.lowkey.R;

public class RegisterActivity4PF extends AppCompatActivity {

    private ImageView back;
    private ImageView edit;
    private Button next;
    private String email;
    private String password;
    private String fullname;
    private String age;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity4_pf);

        initUI();

        getIntentExtras();
    }

    private void initUI(){
        back = findViewById(R.id.back);
        edit = findViewById(R.id.imageView2);
        next = findViewById(R.id.next3);
    }

    private void getIntentExtras(){
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        fullname = getIntent().getStringExtra("fullname");
        age = getIntent().getStringExtra("age");
        gender = getIntent().getStringExtra("gender");
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
                register();
            }
        });
    }

    private void register(){}
}
