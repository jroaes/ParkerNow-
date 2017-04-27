package com.estacionate.jd.parkernow.map.markers;

import android.graphics.drawable.Drawable;

import com.estacionate.jd.parkernow.map.FragmentMapView;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;


/**
 */
public abstract class LayerMarker extends Marker {
    private String id;
    private MarkerInfoWindow maxBubble;
    private MarkerInfoWindow minBubble;
    private FragmentMapView mapView;
    private boolean minimized;

    public LayerMarker(
            FragmentMapView mapView,
            final OnLayerMarkerClickListener clickListener,
            Drawable drawable,
            GeoPoint initPosition,
            String id) {

        super(mapView);
        this.setOnClickListener(clickListener);
        this.setPosition(initPosition);
        this.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        this.setIcon(drawable);
        this.id = id;
        this.mapView = mapView;
        this.minimized = false;
    }

    private void setOnClickListener(final OnLayerMarkerClickListener listener){
        this.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                listener.onMarkerClick(LayerMarker.this);
                return true;
            }
        });
    }
    public String getMarkerId() {
        return id;
    }

    public void fakeClick(){
        this.mOnMarkerClickListener.onMarkerClick(this, getMapView());
    }

    public void maximizeInfoWindow() {
        super.closeInfoWindow();
        if (maxBubble != null){
            this.setInfoWindow(maxBubble);
            this.showInfoWindow();
        }
        this.minimized = false;
    }

    public void minimizeInfoWindow() {
        super.closeInfoWindow();
        if (minBubble != null) {
            this.setInfoWindow(minBubble);
            this.showInfoWindow();
        }
        this.minimized = true;
    }

    public boolean isMinimize() {
        return this.minimized;
    }

    public void openDefaultInfoWindow(){
        maximizeInfoWindow();
    }

    public FragmentMapView getMapView() {
        return mapView;
    }

    protected void setMaxInfoWindow(MarkerInfoWindow maxBubble) {
        this.maxBubble = maxBubble;
    }

    protected void setMinInfoWindow(MarkerInfoWindow minBubble) {
        this.minBubble = minBubble;
    }

    public void resetInfoWindow() {
        if (isMinimize()){
            minimizeInfoWindow();
        } else {
            maximizeInfoWindow();
        }
    }
}
