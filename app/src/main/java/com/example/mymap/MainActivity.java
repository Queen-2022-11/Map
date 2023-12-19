package com.example.mymap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;

import com.example.mymap.Model.Park;
import com.example.mymap.data.Repository;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "Maps";
    private GoogleMap mMap;
    private LatLng mountEverest = new LatLng(28.001377, 86.928129);
    private LatLng mountKilimanjaro = new LatLng(-3.075558, 37.344363);
    private LatLng theAlps = new LatLng(47.368955, 9.702579);

    //Todo: Create Markers for each mountain
    private Marker everestMarker;
    private Marker kelimanjaroMarker;
    private Marker theAlpsMarker;

    private MarkerOptions everestOptions;
    private MarkerOptions theAlpsOptions;

    private List<MarkerOptions> markerOptionsList;

    private ArrayList<Marker> markerArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if (id == R.id.maps_nav_menu) {
                // Handle maps_nav_menu click
                mMap.clear();
                getSupportFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
                mapFragment.getMapAsync(this);
                return true;
            } else if (id == R.id.parks_nav_menu) {
                selectedFragment = ParksFragment.newInstance();
            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.map, selectedFragment).commit();

            return true;
        });
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

       /* markerArrayList = new ArrayList<>();

        markerOptionsList = new ArrayList<>();



        everestOptions = new MarkerOptions().position(mountEverest)
                .title("Everest")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        markerOptionsList.add(everestOptions);

        theAlpsOptions = new MarkerOptions().position(theAlps)
                .title("Alps")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        markerOptionsList.add(theAlpsOptions);*/
        Repository.getParks(parks -> {
            for (Park park : parks) {
                String latitude = park.getLatitude();
                String longitude = park.getLongitude();

                if (!latitude.isEmpty() && !longitude.isEmpty()) {
                    double lat = Double.parseDouble(latitude);
                    double lng = Double.parseDouble(longitude);
                    LatLng latLng = new LatLng(lat, lng);

                    mMap.addMarker(new MarkerOptions().position(latLng).title(park.getFullName()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    Log.d("park", "OnMapReady\t\t" + park.getFullName());
                }
            }
        });


//        kelimanjaroMarker = mMap.addMarker(new MarkerOptions()
//                .position(mountKilimanjaro)
//                .title("Mt Kelimanjaro")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//
//         markerArrayList.add(kelimanjaroMarker);
//
//        theAlpsMarker = mMap.addMarker(new MarkerOptions()
//                .position(theAlps)
//                .title("The Alps")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
//        );
//        markerArrayList.add(theAlpsMarker);
//
//        everestMarker = mMap.addMarker(new MarkerOptions()
//                .position(mountEverest)
//                .title("Mt. Everest")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
//
//        markerArrayList.add(everestMarker);
/*
        for (MarkerOptions options : markerOptionsList) {
            LatLng latLng = new LatLng(options.getPosition().latitude, options.getPosition().longitude);
            mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 4)); // 1 - 20


            //Log.d(TAG, "onMapReady: " + marker.getTitle());
        }*/

//        for (Marker marker : markerArrayList) {
//            LatLng latLng = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
//            mMap.addMarker(new MarkerOptions().position(latLng));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 4)); // 1 - 20
//
//
//            //Log.d(TAG, "onMapReady: " + marker.getTitle());
//        }



//        // Add a marker in Sydney and move the camera
//        LatLng binga = new LatLng(-19.7766658, 33.0444344);
//        LatLng sydney = new LatLng(-34, 151);
//
//        mMap.addMarker(new MarkerOptions().position(binga).title("Marker in Binga")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
//                .alpha(0.8f));
//        //mMap.moveCamera(CameraUpdateFactory.newLatLng(binga));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(binga, 13)); // 1 - 20
    }
}
