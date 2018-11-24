package stargazing.lowkey.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import stargazing.lowkey.R;

public class MapFragmentFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final String KEY_POSITION = "position";
    //If there's need for a callback from the Parrent

    static MapFragmentFragment newInstance(int position) {
        MapFragmentFragment frag = new MapFragmentFragment();
        Bundle args = new Bundle();
//f
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return (frag);
    }



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_fragment_map, container, false);
        int position = getArguments().getInt(KEY_POSITION, -1);
        //mainCallback.DoSomething();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return (result);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(45, 21);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


}
