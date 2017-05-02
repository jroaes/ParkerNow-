package com.estacionate.jd.parkernow.fragments;

/**
 * Created by Jorge on 26-04-2017.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estacionate.jd.parkernow.R;
import com.estacionate.jd.parkernow.backend.Parking;
import com.estacionate.jd.parkernow.backend.ParkingAll;
import com.estacionate.jd.parkernow.map.LoadMarkers;
import com.estacionate.jd.parkernow.utils.Constants;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;


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

        GpsMyLocationProvider mLocation = new GpsMyLocationProvider(this.getContext());
        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(mLocation,this.mapView);
        mLocationOverlay.enableMyLocation();
        this.mapView.getOverlays().add(mLocationOverlay);

        /*OverlayItem myLocationOverlayItem = new OverlayItem("Here", "Current Position", currentLocation);
        Drawable myCurrentLocationMarker = this.getResources().getDrawable(R.drawable.person);
        myLocationOverlayItem.setMarker(myCurrentLocationMarker);

        final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(myLocationOverlayItem);

        ItemizedIconOverlay<OverlayItem> currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        return true;
                    }
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return true;
                    }
                }, resourceProxy);
        this.mapView.getOverlays().add(currentLocationOverlay);*/

        LoadMarkers parkings = new LoadMarkers(this.mapView,new GeoPoint(Constants.concepcionLatitude, Constants.concepcionLongitude));

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
