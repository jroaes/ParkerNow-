package com.estacionate.jd.parkernow.map.markers;

import android.view.View;

import com.estacionate.jd.parkernow.R;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

/**
 * Created by Jorge on 08-05-2017.
 */

public class ParkingInfoWindow extends MarkerInfoWindow{

    private boolean isFirstTimeOpen = true;
    private boolean isOpen = true;
    private Marker item;
    /**
     * @param layoutResId layout that must contain these ids: bubble_title,bubble_description,
     *                    bubble_subdescription, bubble_image
     * @param mapView
     */
    public ParkingInfoWindow(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }

    public ParkingInfoWindow(MapView mapView) {
        super(R.layout.bubble_example, mapView);
    }
    @Override
    public void onOpen(Object item){
        super.onOpen(item);
        this.item = (Marker)item;
        if(!isFirstTimeOpen){
            if(isOpen){
                this.getView().setVisibility(View.INVISIBLE);
                isOpen = false;
            }
            else{
                this.getView().setVisibility(View.VISIBLE);
                isOpen = true;
            }
        }
        isFirstTimeOpen = false;
    }
}
