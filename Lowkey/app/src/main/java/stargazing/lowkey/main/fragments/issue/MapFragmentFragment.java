package stargazing.lowkey.main.fragments.issue;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
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
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import com.google.android.gms.tasks.OnSuccessListener;

import stargazing.lowkey.LowkeyApplication;
import stargazing.lowkey.R;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.OnSuccessListHandler;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.managers.IssueManager;
import stargazing.lowkey.models.IssueGetModel;
import stargazing.lowkey.models.IssueModel;
import stargazing.lowkey.serializers.IssueSerializer;

import static android.app.Activity.RESULT_OK;
import static java.util.UUID.randomUUID;
import static stargazing.lowkey.auth.register.RegisterActivity2FL.REQUEST_LOCATION;

public class MapFragmentFragment extends Fragment implements OnMapReadyCallback, RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    private GoogleMap mMap;
    private static float ZOOM = 19, maxRadius = 13;
    private static final String KEY_POSITION = "position";

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    private ImageView imageview1, imageview2, imageview3, imageview4, imageview5, imageview6;
    private Button addphoto;
    private EditText title, description;
    private ConstraintLayout addIssueslayout;

    ConstraintLayout cc;
    private TextView title1,description1,up,down;

    private double currentLat;
    private double currentLong;
    private View viewToExpand;
    public View result;
    private Uri file;
    FusedLocationProviderClient mfusedlocation;
    private ArrayList<IssueModel> issues = new ArrayList<>();
    ArrayList<IssueGetModel> arrayList = new ArrayList<>();
    private IssueManager issueManager = new IssueManager();

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
        viewToExpand = result.findViewById(R.id.view);
        collapse(viewToExpand,200,1);
        addIssueslayout = result.findViewById(R.id.addIssue);
        addIssueslayout.setVisibility(View.INVISIBLE);
        cc = result.findViewById(R.id.cc);
        title1 = result.findViewById(R.id.title);
        description1 = result.findViewById(R.id.description);
        up = result.findViewById(R.id.up);
        down = result.findViewById(R.id.down);
        rfaLayout = result.findViewById(R.id.activity_main_rfal);
        rfaBtn = result.findViewById(R.id.activity_main_rfab);
        if(getActivity().getIntent().getStringExtra("Status")!=null)
            if(!getActivity().getIntent().getStringExtra("Status").equals("offline"))
            initFabs();
        initPhotos(result);

        return (result);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String locAddress = marker.getTitle();
                Log.w("c","clicked");
                expand(viewToExpand,1500,450);
                ConstraintLayout cc = result.findViewById(R.id.cc);

                IssueGetModel issueGetModel = new IssueSerializer((String)marker.getTag()).getIssueGetModel();


                try {
                    title1.setText(issueGetModel.getTitle());
                    description1.setText(issueGetModel.getDescription());
                    //up.setText(issueGetModel.getUpVotes());
                    //down.setText(issueGetModel.getDownVotes());
                } catch (NullPointerException npe){

                }

                cc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        collapse(viewToExpand,1000,1);
                    }
                });
                return true;
            }
        });
        //LatLng currentLocation = new LatLng(lat, lon);
        //mMap.addMarker(new MarkerOptions().position(currentLocation).title("You"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        //zoomMap(mMap, lat, lon, radius);
        putUserOnTheMap();
        putIssues(mMap);
    }

    private void putUserOnTheMap(){
        mfusedlocation = LocationServices.getFusedLocationProviderClient(getContext());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            mfusedlocation.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Log.e("LOCATION","X:"+ String.valueOf(location.getLatitude()) + "Y :" + String.valueOf(location.getLongitude()));
                                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("You"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                                zoomMap(mMap,location.getLatitude(),location.getLongitude(),13);
                                currentLat = location.getLatitude();
                                currentLong = location.getLongitude();
                            }
                        }
                    });
        }
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


    private void changeRadius() {

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


    private void putIssues(final GoogleMap gooMap) {
        issueManager.getAll(new OnSuccessListHandler() {
            @Override
            public void handle(JSONArray response) {
                if(!response.equals(RequestWrapper.FAIL_JSON_LIST_RESPONSE_VALUE)) {
                    arrayList = issueManager.getIssues();
                    for (IssueGetModel g : arrayList) {
                        LatLng location = new LatLng(g.getLatitude(), g.getLongitude());
                        String s = g.getTitle() + "\n" + g.getDescription() + "\n" + "Posted by : " + g.getCreator();
                        Marker amarker = gooMap.addMarker(new MarkerOptions().position(location).title(s));
                        IssueSerializer issueSerializer = new IssueSerializer(g);
                        amarker.setTag(issueSerializer.getIssueStringSerialized());


                    }
                }
            }
        });
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
                    currentLat,
                    currentLong,
                    LowkeyApplication.currentUserManager.getUserModel().getId(),
                    photoStrings
            );
            IssueManager issueManager = new IssueManager();
            issueManager.createIssue(toGo, new OnSuccessHandler() {
                @Override
                public void handle(JSONObject response) {
                    if (!response.equals(RequestWrapper.FAIL_JSON_RESPONSE_VALUE))
                        Toast.makeText(getContext().getApplicationContext(), "Issue added to the city map !", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getContext().getApplicationContext(), "Something went wrong !", Toast.LENGTH_LONG).show();
                }
            });
            return toGo;
        } else {
            return null;
        }
    }

    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight = v.getHeight();
        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }


}
