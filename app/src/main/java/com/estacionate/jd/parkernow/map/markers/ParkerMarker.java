package com.estacionate.jd.parkernow.map.markers;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.estacionate.jd.parkernow.map.FragmentMapView;
import com.estacionate.jd.parkernow.parkers.Parker;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

/**
 * Created by Jorge on 27-04-2017.
 */

public class ParkerMarker extends LayerMarker {
    private Parker parker;
    private FragmentMapView mapView;

    public ParkerMarker(Parker parker, FragmentMapView mapView,
                     OnLayerMarkerClickListener clickListener, Drawable drawable) {
        super(mapView, clickListener, drawable,
                new GeoPoint(parker.getLat(), parker.getLng()), parker.getId());

        this.parker = parker;
        this.mapView = mapView;

    }

    @Override
    public void closeInfoWindow(){
        minimizeInfoWindow();
    }

    public void forceCloseInfoWindow(){
        super.closeInfoWindow();
    }

    public Parker getParker() {
        return parker;
    }


}
