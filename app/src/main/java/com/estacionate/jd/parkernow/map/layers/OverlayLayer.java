package com.estacionate.jd.parkernow.map.layers;

import com.estacionate.jd.parkernow.map.FragmentMapView;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Overlay;

import java.util.List;


public abstract class OverlayLayer {

    private final FragmentMapView mapView;
    private final Type layerType;
    private LayerParent parent;
    private boolean dirty;
    protected final String TAG = this.getClass().getSimpleName();

    // Possibles types of layers
    public enum Type {
        USER, PARKER, ROUTE
    }

    public OverlayLayer(LayerParent parent, Type layerType){
        this.mapView = parent.getMapViewInstance();
        this.layerType = layerType;
        this.dirty = true;
        this.parent = parent;
    }

    public abstract void onMapDisplacement(int zoomLevel, double centerLat, double centerLon);
    public abstract void onUserLocationChange(GeoPoint location);
    public abstract void clear();
    public abstract void addOverlaysToOverlayList(List<Overlay> overlayList);

    public FragmentMapView getMapView() {
        return mapView;
    }
    public Type getLayerType() {
        return layerType;
    }
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    public boolean isDirty() {
        return dirty;
    }
    public void changeParent(LayerParent newParent) {
        this.parent = newParent;
    }
    protected void askForRedraw() {
        parent.askForRedraw();
    }
}
