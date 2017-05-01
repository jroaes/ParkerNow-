package com.estacionate.jd.parkernow.fragments;

/**
 * Created by Jorge on 26-04-2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estacionate.jd.parkernow.R;
import com.estacionate.jd.parkernow.backend.Parking;
import com.estacionate.jd.parkernow.utils.Constants;

import org.osmdroid.util.GeoPoint;


/**
 * Fragment that holds the OsmDroid Map
 */
public class ReportMapFragment extends MapFragment {

    // Debugging Tag
    private final static String TAG = "ReportMapFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createLocationRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        Bundle arguments = getArguments();
        this.mapView.getController().setCenter(new GeoPoint(Constants.concepcionLatitude, Constants.concepcionLongitude));
        if (arguments != null) { //The splash screen got the user position and is in the bundle
            Double lat = arguments.getDouble("first_lat");
            Double lon = arguments.getDouble("first_lon");
            this.mapView.getController().setCenter(new GeoPoint(lat, lon));
            currentPosition = new GeoPoint(lat, lon);
        }
        /*else{
            this.mapView.getController().setCenter(new GeoPoint(Constants.concepcionLatitude, Constants.concepcionLongitude));
        }*/

        Parking parking = new Parking();
        parking.getAllParker();
        Log.d(TAG, "End CreateView");


        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.map_layout;
    }

    @Override
    protected void onScroll() {
        //positionCenterToggleButton.onScroll();
    }

    @Override
    protected void onFakeScroll() {
        //positionCenterToggleButton.unchecked();
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        innerOnResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        innerOnPause();
    }

    public void innerOnPause() {

    }

    public void innerOnResume(){
        mapView.getController().animateTo(currentPosition);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    protected void createLocationRequest() {

    }

}
