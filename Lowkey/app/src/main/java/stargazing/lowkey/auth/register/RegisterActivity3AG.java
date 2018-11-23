package stargazing.lowkey.auth.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import stargazing.lowkey.R;

public class RegisterActivity3AG extends AppCompatActivity {


    private Button next;
    private ImageView back;
    private DatePicker dpBirth;
    private Spinner spinnerGender;
    private Spinner spinnerRadius;
    private String email;
    private String password;
    private String fullname;
    public Double latitude, longitude;


    private static String ERROR_BAD_EMAIL = "Email or password is not valid !";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity3_ag);
        StatusBarUtil.setTransparent(this);
        initUI();

        getIntentExtras();

        setOnClickListeners();
    }

    private void getIntentExtras(){
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        fullname = getIntent().getStringExtra("fullname");
        longitude = getIntent().getDoubleExtra("longitude",0.0f);
        latitude = getIntent().getDoubleExtra("latitude",0.0f);
    }


    private void initUI() {
        dpBirth = findViewById(R.id.dpBirth);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerRadius = findViewById(R.id.spinnerGender2);
        next = findViewById(R.id.next2);
        back = findViewById(R.id.back2);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.
                createFromResource(this, R.array.genderArray, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(spinnerAdapter);

        ArrayAdapter<CharSequence> spinnerAdapter1 = ArrayAdapter.
                createFromResource(this, R.array.radius, android.R.layout.simple_spinner_item);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRadius.setAdapter(spinnerAdapter);


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
                if(readyToGo()) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity4PF.class);
                    intent.putExtra("email",email);
                    intent.putExtra("password",password);
                    intent.putExtra("fullname",fullname);
                    intent.putExtra("age",getAge());
                    intent.putExtra("gender",spinnerGender.getSelectedItem().toString());
                    intent.putExtra("radius",spinnerRadius.getSelectedItem().toString());
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }else
                    Toast.makeText(getApplicationContext(), ERROR_BAD_EMAIL, ERROR_BAD_EMAIL.length());
            }
        });
    }

    private boolean readyToGo(){
        if(dpBirth.getYear() > 2004) {
            Toast.makeText(getApplicationContext(),"You are too young bro",Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    private int getAge(){
        int day = dpBirth.getDayOfMonth();
        int month = dpBirth.getMonth();
        int year = dpBirth.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        Date date = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int an = Calendar.getInstance().get(Calendar.YEAR);
        int age = an - year;
        return age;
    }
}
