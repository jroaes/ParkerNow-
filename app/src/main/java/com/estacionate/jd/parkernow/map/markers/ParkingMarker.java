package com.estacionate.jd.parkernow.map.markers;

import android.graphics.drawable.Drawable;

import com.estacionate.jd.parkernow.backend.Parking;
import com.estacionate.jd.parkernow.map.FragmentMapView;

import org.osmdroid.util.GeoPoint;

/**
 * Created by Jorge on 27-04-2017.
 */

public class ParkingMarker extends LayerMarker {
    private Parking parking;
    private FragmentMapView mapView;

    public ParkingMarker(Parking parking, FragmentMapView mapView,
                         OnLayerMarkerClickListener clickListener, Drawable drawable) {
        super(mapView, clickListener, drawable,
                new GeoPoint(parking.getLat(), parking.getLng()), parking.getId());

        this.parking = parking;
        this.mapView = mapView;

    }

    @Override
    public void closeInfoWindow(){
        minimizeInfoWindow();
    }

    public void forceCloseInfoWindow(){
        super.closeInfoWindow();
    }

    public Parking getParking() {
        return parking;
    }


}
