package stargazing.lowkey.auth;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import stargazing.lowkey.R;
import stargazing.lowkey.utils.GeoLocationManager;

public class RegisterActivity2FL extends AppCompatActivity {

    private EditText fullname;
    private Button next;
    private ImageView back;
    private TextView locationDisplay;

    protected Context context;
    protected Double latitude,longitude;

    private String email;
    private String password;

    private static String ERROR_BAD_EMAIL = "Email or password is not valid !";

    private boolean LOCATION_STATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity2_fl);

        initUI();

        getIntentExtras();

        setOnClickListeners();

    }

    private void getIntentExtras(){
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
    }

    private void initUI() {
        fullname = findViewById(R.id.fullname);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back1);
        locationDisplay = findViewById(R.id.location);
        getlocation();
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
                if(readyToGo(String.valueOf(fullname.getText()))) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity3AG.class);
                    intent.putExtra("fullname",String.valueOf(fullname.getText()));
                    intent.putExtra("email",email);
                    intent.putExtra("password",password);
                    startActivity(intent);
                }else
                    Toast.makeText(getApplicationContext(), ERROR_BAD_EMAIL, ERROR_BAD_EMAIL.length());
            }
        });
    }

    private boolean readyToGo(String fullname){
    if(!fullname.equals("") && LOCATION_STATE)
            return true;
        return false;
    }

    private void getlocation(){
        GeoLocationManager geoLocationManager = new GeoLocationManager(this);
        //geoLocationManager.onLocationChanged();
        LOCATION_STATE = true;
    }
}


