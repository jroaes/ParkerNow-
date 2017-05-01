package com.estacionate.jd.parkernow.map;

import com.estacionate.jd.parkernow.map.layers.OverlayLayer;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge on 01-05-2017.
 */

public class LoadMarkers {

    FragmentMapView mapView;
    GeoPoint userPosition;
    private List<OverlayLayer> layers;

    public LoadMarkers(FragmentMapView mapView, GeoPoint userStartPoint){
        this.mapView = mapView;
        this.userPosition = userStartPoint;
        this.layers = new ArrayList<>();
    }

    protected FragmentMapView getMapView() {
        return mapView;
    }

    protected void addLayer(OverlayLayer layer) {
        //layer.changeParent(this);
        this.layers.add(layer);
    }

    private void addLayersMarkersToMap(MapView mapView) {
        for(OverlayLayer layer : this.layers) {
            layer.addOverlaysToOverlayList(mapView.getOverlays());
            layer.setDirty(false);
        }
        //mapView.getOverlays().add(0, this.mapEventsOverlay);
    }
}
