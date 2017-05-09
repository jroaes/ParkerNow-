package com.estacionate.jd.parkernow.map;

import com.estacionate.jd.parkernow.ParkingNowApp;
import com.estacionate.jd.parkernow.R;
import com.estacionate.jd.parkernow.backend.Parking;
import com.estacionate.jd.parkernow.backend.ParkingAll;
import com.estacionate.jd.parkernow.map.layers.OverlayLayer;
import com.estacionate.jd.parkernow.map.markers.ParkMarker;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jorge on 01-05-2017.
 */

public class LoadMarkers implements Observer {

    MapView mapView;
    GeoPoint userPosition;
    ArrayList<ParkMarker> parkMarkers;
    private List<OverlayLayer> layers;
    private ParkingAll parkings;

    public LoadMarkers(MapView mapView, GeoPoint userStartPoint){
        this.mapView = mapView;
        this.userPosition = userStartPoint;
        this.layers = new ArrayList<>();
        this.parkMarkers = new ArrayList<ParkMarker>();
        initiallize();
    }

    protected MapView getMapView() {
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

    private void initiallize(){
        parkings = new ParkingAll();
        parkings.addObserver(this);
    }

    private void addToMap(String tag){
        if(tag.equals("ParkingAll")) {
            List<Parking> Allparking = parkings.getAllParkings();
            for (Parking parking : Allparking) {
                parkMarkers.add(new ParkMarker(parking,this.mapView));
            }
        }
        else if(tag.equals("ParkingClose")){
            deleteAllMarker();
            List<Parking> Allparking = parkings.getAllParkings();
        }
    }

    private void deleteAllMarker(){
        for(ParkMarker parkingMarker: parkMarkers){
            parkingMarker.remove();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        addToMap(o.toString());
    }
}
