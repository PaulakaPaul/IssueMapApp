package stargazing.lowkey.auth.register;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jaeger.library.StatusBarUtil;

import stargazing.lowkey.R;
import stargazing.lowkey.utils.GPSTrackerActivity;

public class RegisterActivity2FL extends AppCompatActivity {

    private EditText fullname;
    private Button next;
    private ImageView back;
    private TextView locationDisplay;
    static public final int REQUEST_LOCATION = 1;

    protected Context context;
    public Double latitude, longitude;
    FusedLocationProviderClient mfusedlocation;
    private String email;
    private String password;

    private static String ERROR_BAD_EMAIL = "Username not valid ! ";

    private boolean LOCATION_STATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity2_fl);
        StatusBarUtil.setTransparent(this);
        initUI();

        getIntentExtras();

        setOnClickListeners();

    }

    private void getIntentExtras() {
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
                if (readyToGo(String.valueOf(fullname.getText()))) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity3AG.class);
                    intent.putExtra("fullname", String.valueOf(fullname.getText()));
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("longitude",longitude);
                    intent.putExtra("latitude",latitude);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else
                    Toast.makeText(getApplicationContext(), ERROR_BAD_EMAIL, ERROR_BAD_EMAIL.length()).show();
            }
        });
    }

    private boolean readyToGo(String fullname) {
        if (!fullname.equals("") && LOCATION_STATE)
            return true;
        return false;
    }

    private void getlocation() {
        mfusedlocation = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            mfusedlocation.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations, this can be null.
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                String s = "Latitude : " + String.valueOf(location.getLatitude()) + " Longitude : "+String.valueOf(location.getLongitude());
                                locationDisplay.setText(s);
                                Log.e("LOCATION","X:"+ String.valueOf(location.getLatitude()) + "Y :" + String.valueOf(location.getLongitude()));
                            }
                        }
                    });
        }
        LOCATION_STATE = true;
    }



    private void initFusedLocation(){

    }
}


