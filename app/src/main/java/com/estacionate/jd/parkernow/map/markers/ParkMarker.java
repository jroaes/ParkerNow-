package com.estacionate.jd.parkernow.map.markers;

import com.estacionate.jd.parkernow.ParkingNowApp;
import com.estacionate.jd.parkernow.R;
import com.estacionate.jd.parkernow.backend.Parking;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

/**
 * Created by Jorge on 08-05-2017.
 */

public class ParkMarker {

    private Parking parking;
    private MapView mapView;
    private Marker parkingMarker;

    public ParkMarker(Parking parking, MapView mapView){
        this.parking = parking;
        this.mapView = mapView;
        initiallize();
    }

    private void initiallize(){
        parkingMarker = new Marker(this.mapView);
        parkingMarker.setPosition(new GeoPoint(parking.getLat(), parking.getLng()));
        parkingMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        this.mapView.getOverlays().add(parkingMarker);
        this.mapView.invalidate();
        parkingMarker.setIcon(ParkingNowApp.getAppContext().getDrawable(R.drawable.ic_parking));
        String title = parking.getName();
        int hour = parking.getVal()*60;
        String description = "Precio: $"+ parking.getVal() + "/min - $" + hour+"/hora";
        parkingMarker.setSnippet(description);
        parkingMarker.setTitle(title);
        parkingMarker.setImage(ParkingNowApp.getAppContext().getDrawable(R.drawable.ic_parking_here));

        parkingMarker.setInfoWindow(new ParkingInfoWindow(this.mapView));
    }

    public void remove(){
        this.mapView.getOverlays().remove(parkingMarker);
    }

}
