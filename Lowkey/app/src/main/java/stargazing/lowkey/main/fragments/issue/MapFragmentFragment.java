package stargazing.lowkey.main.fragments.issue;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import stargazing.lowkey.LowkeyApplication;
import stargazing.lowkey.R;
import stargazing.lowkey.models.IssueModel;

import static android.app.Activity.RESULT_OK;
import static java.util.UUID.randomUUID;

public class MapFragmentFragment extends Fragment implements OnMapReadyCallback, RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    private GoogleMap mMap;
    private static float ZOOM = 19, maxRadius = 13;
    private static final String KEY_POSITION = "position";

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    private ImageView imageview1,imageview2,imageview3,imageview4,imageview5,imageview6;
    private Button addphoto;
    private EditText title,description;
    private ConstraintLayout addIssueslayout;

    public View result;
    private Uri file;

    private ArrayList<IssueModel> issues = new ArrayList<>();

    static MapFragmentFragment newInstance(int position) {
        MapFragmentFragment frag = new MapFragmentFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return (frag);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        result = inflater.inflate(R.layout.fragment_fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addIssueslayout = result.findViewById(R.id.addIssue);
        addIssueslayout.setVisibility(View.INVISIBLE);

        rfaLayout = result.findViewById(R.id.activity_main_rfal);
        rfaBtn = result.findViewById(R.id.activity_main_rfab);

        initFabs();
        initPhotos(result);

        return (result);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        // double lat = LowkeyApplication.currentUserManager.getUserModel().getDoubleLat();
        // double lon = LowkeyApplication.currentUserManager.getUserModel().getDoubleLon();
        // int radius = LowkeyApplication.currentUserManager.getUserModel().getRadius();
        double lat = 45.75372f;
        double lon = 21.22571f;
        int radius = 13;
        LatLng currentLocation = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("You"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        zoomMap(mMap, lat, lon, radius);

        ArrayList<String> list = new ArrayList<String>();
        list.add("dasda");
        IssueModel issueModel = new IssueModel(randomUUID(), "fafas", "fasfasfa", 45.65372f, 21.13571f, randomUUID(), list);
        IssueModel issueModel1 = new IssueModel(randomUUID(), "fafas", "fasfasfa", 45.65472f, 21.14571f, randomUUID(), list);
        IssueModel issueModel2 = new IssueModel(randomUUID(), "fafas", "fasfasfa", 45.62572f, 21.15571f, randomUUID(), list);
        IssueModel issueModel3 = new IssueModel(randomUUID(), "fafas", "fasfasfa", 45.67372f, 21.16571f, randomUUID(), list);

        issues.add(issueModel);
        issues.add(issueModel1);
        issues.add(issueModel2);
        issues.add(issueModel3);
        putIssues(mMap, issues);
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        Toast.makeText(getContext(), "clicked label: " + position, Toast.LENGTH_SHORT).show();
        if (position == 0) {
            addIssueslayout.setVisibility(View.VISIBLE);
            initPhotos(result);
        } else {
            //changeRadius();
        }
        rfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        Toast.makeText(getContext(), "clicked icon: " + position, Toast.LENGTH_SHORT).show();
        if (position == 0) {
            addIssueslayout.setVisibility(View.VISIBLE);
            initPhotos(result);
        } else {
            //changeRadius();
        }
        rfabHelper.toggleContent();
    }


    private void changeRadius(){

    }

    private void takePhoto(int status) {
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            addphoto.setEnabled(false);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".my.package.name.provider", getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100 + status);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                addphoto.setEnabled(true);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Picasso.get().load(file).resize(50, 50).centerCrop().into(imageview1);
            }
        }
        if (requestCode == 102) {
            if (resultCode == RESULT_OK) {
                Picasso.get().load(file).resize(50, 50).centerCrop().into(imageview2);
            }
        }
        if (requestCode == 103) {
            if (resultCode == RESULT_OK) {
                Picasso.get().load(file).resize(50, 50).centerCrop().into(imageview3);
            }
        }
        if (requestCode == 104) {
            if (resultCode == RESULT_OK) {
                Picasso.get().load(file).resize(50, 50).centerCrop().into(imageview4);
            }
        }
        if (requestCode == 105) {
            if (resultCode == RESULT_OK) {
                Picasso.get().load(file).resize(50, 50).centerCrop().into(imageview5);
            }
        }
        if (requestCode == 106) {
            if (resultCode == RESULT_OK) {
                Picasso.get().load(file).resize(50, 50).centerCrop().into(imageview6);
            }
        }
    }


    private void zoomMap(GoogleMap gooMap, double selectedLat, double selectedLong, int radius) {

        float currentZoomLevel = calculateRadius(radius);
        float animateZomm = currentZoomLevel + 5;

        Log.e("Zoom Level:", currentZoomLevel + "");
        Log.e("Zoom Level Animate:", animateZomm + "");

        gooMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(selectedLat, selectedLong), animateZomm));
        gooMap.animateCamera(CameraUpdateFactory.zoomTo(currentZoomLevel), 2000, null);
        Log.e("Circle Lat Long:", selectedLat + ", " + selectedLong);
    }


    private void putIssues(GoogleMap gooMap, ArrayList<IssueModel> arrayList) {
        for (IssueModel i : arrayList) {
            LatLng location = new LatLng(i.getLatitude(), i.getLongitude());
            gooMap.addMarker(new MarkerOptions().position(location).title("Issue !!!"));
        }
    }

    /***
     * @INIT
     */
    private void initPhotos(View v) {
        title = v.findViewById(R.id.editText2);
        description = v.findViewById(R.id.editText3);
        addphoto = v.findViewById(R.id.next4);
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                addIssueslayout.setVisibility(View.INVISIBLE);
            }
        });
        imageview1 = v.findViewById(R.id.imageView3);
        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(1);
            }
        });
        imageview2 = v.findViewById(R.id.imageView4);
        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(2);

            }
        });
        imageview3 = v.findViewById(R.id.imageView5);
        imageview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(3);

            }
        });
        imageview4 = v.findViewById(R.id.imageView6);
        imageview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(4);

            }
        });
        imageview5 = v.findViewById(R.id.imageView7);
        imageview5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(5);

            }
        });
        imageview6 = v.findViewById(R.id.imageView8);
        imageview6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(6);

            }
        });
    }

    private void initFabs() {

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getContext().getApplicationContext());
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Add issue")
                .setResId(R.mipmap.ic_launcher)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Change radius")
                .setResId(R.mipmap.ic_launcher)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(1)
        );
        rfaContent
                .setItems(items)
        //.setIconShadowRadius(ABTextUtil.dip2px(context, 5))
        //.setIconShadowColor(0x000)
        //.setIconShadowDy(ABTextUtil.dip2px(context, 5))
        ;
        rfabHelper = new RapidFloatingActionHelper(
                getContext().getApplicationContext(),
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();
    }

    /***
     * @UTILS
     */
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    private float calculateRadius(int radius) {
        return (radius * ZOOM) / maxRadius;
    }

    private Bitmap imageviewToBitmap(ImageView imag) {

        return ((BitmapDrawable) imag.getDrawable()).getBitmap();


    }

    private IssueModel getData() {
        if (!title.getText().toString().equals("") && !description.getText().toString().equals("")) {
            ArrayList<String> photoStrings = new ArrayList<>();
            Bitmap b = imageviewToBitmap(imageview1);
            photoStrings.add(getEncoded64ImageStringFromBitmap(b));
            b = imageviewToBitmap(imageview2);
            photoStrings.add(getEncoded64ImageStringFromBitmap(b));
            b = imageviewToBitmap(imageview3);
            photoStrings.add(getEncoded64ImageStringFromBitmap(b));
            b = imageviewToBitmap(imageview4);
            photoStrings.add(getEncoded64ImageStringFromBitmap(b));
            b = imageviewToBitmap(imageview5);
            photoStrings.add(getEncoded64ImageStringFromBitmap(b));
            b = imageviewToBitmap(imageview6);
            photoStrings.add(getEncoded64ImageStringFromBitmap(b));
            for (String s : photoStrings)
                Log.e("STAUTS", s);
            IssueModel toGo = new IssueModel(
                    randomUUID(),
                    title.getText().toString(),
                    description.getText().toString(),
                    LowkeyApplication.currentUserManager.getUserModel().getDoubleLat(),
                    LowkeyApplication.currentUserManager.getUserModel().getDoubleLon(),
                    LowkeyApplication.currentUserManager.getUserModel().getId(),
                    photoStrings
            );
            return toGo;
        } else {
            return null;
        }
    }


}
