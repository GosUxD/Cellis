package com.example.daniel.mystory;

import android.app.Dialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by daniel on 18.1.16.
 */
public class CopiedMain extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private static final int REQUEST_CODE = 0;
    private static final String TAG = "LocationTag";
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .build();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = new SupportMapFragment();
        fragmentManager.beginTransaction()
                .add(R.id.activity_main_drawer_layout, mapFragment)
                .commit();


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
            }
        });


    }

    private void updateUI() {
        if (mMap == null || mLastLocation == null) {
            return;
        }

        LatLng myPoint = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        MarkerOptions myMarker = new MarkerOptions()
                .position(myPoint);
        mMap.clear();
        mMap.addMarker(myMarker);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(myPoint, 15.0f);
        mMap.animateCamera(update);
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, createLocationRequest(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = GooglePlayServicesUtil
                    .getErrorDialog(errorCode, this, REQUEST_CODE,
                            new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    finish();
                                }
                            });
            errorDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Getting last known location...");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Log.i(TAG, "First Location is: \n Lat: " + mLastLocation.getLatitude()
                    + "\n Long: " + mLastLocation.getLongitude());
        }
        updateUI();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        Log.i(TAG, " Lat: " + mLastLocation.getLatitude()
                + "\n Long: " + mLastLocation.getLongitude());
    }
}

