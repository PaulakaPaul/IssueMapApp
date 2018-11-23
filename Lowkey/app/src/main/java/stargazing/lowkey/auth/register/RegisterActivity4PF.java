package stargazing.lowkey.auth.register;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import stargazing.lowkey.R;

public class RegisterActivity4PF extends AppCompatActivity {

    private ImageView back;
    private CircleImageView edit;
    private Button next;
    private Button getPhoto;
    private String email;
    private String password;
    private String fullname;
    private String age;
    private String gender;

    public Double latitude, longitude;


    public static final int SMALL = 1;
    public static final int MEDIUM = 2;
    public static final int LARGE = 3;
    private static final float PHOTO_SCALE_RATIO_BIG = 0.07f;
    private static final float PHOTO_SCALE_RATIO_SMALL = 0.2f;
    private static final int PHOTO_THRESHOLD = 2000;


    private final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity4_pf);
        StatusBarUtil.setTransparent(this);
        initUI();

        getIntentExtras();

        setOnClickListeners();
    }

    private void initUI(){
        back = findViewById(R.id.back);
        edit = findViewById(R.id.imageView2);
        next = findViewById(R.id.next3);
        getPhoto = findViewById(R.id.getPhoto);
    }

    private void getIntentExtras(){
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        fullname = getIntent().getStringExtra("fullname");
        age = getIntent().getStringExtra("age");
        gender = getIntent().getStringExtra("gender");
        longitude = getIntent().getDoubleExtra("longitude",0.0f);
        latitude = getIntent().getDoubleExtra("latitude",0.0f);
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
                overridePendingTransition(0, 0);
            }
        });
        getPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(RegisterActivity4PF.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        askForImage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void askForImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        // Resize image before saving it.
                        bitmap = resizeBitmap(bitmap,SMALL);
                        edit.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("GalleryRequest", e.getMessage());
                    }
                    break;
            }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int mode) {
        int width = scaleValue
                (bitmap.getWidth(), bitmap),
                height = scaleValue(bitmap.getHeight(), bitmap);

        if (mode > LARGE)
            mode = LARGE;
        else if(mode < SMALL)
            mode = SMALL;

        width *= mode;
        height *= mode;

        bitmap = Bitmap.createScaledBitmap(bitmap,
                width,
                height,
                true);

        return bitmap;
    }

    public static int scaleValue(int value, Bitmap photo) {
        if (photo.getWidth() >= PHOTO_THRESHOLD || photo.getHeight() >= PHOTO_THRESHOLD)
            return Math.round(PHOTO_SCALE_RATIO_BIG * value);
        else
            return Math.round(PHOTO_SCALE_RATIO_SMALL * value);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 1);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }

    private void register(){}
}
