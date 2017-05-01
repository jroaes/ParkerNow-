package com.estacionate.jd.parkernow.map.layers;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.estacionate.jd.parkernow.map.markers.LayerMarker;
import com.estacionate.jd.parkernow.map.markers.OnLayerMarkerClickListener;

import org.osmdroid.views.overlay.Overlay;

import java.util.ArrayList;
import java.util.List;


public abstract class MarkerLayer<T extends LayerMarker> extends OverlayLayer
        implements OnLayerMarkerClickListener<T> {

    // listener to control the user actions in this layer.
    //private OnMapInteractionListener<T> mapListener;
    // list of markers in charge of this layer.
    private List<T> markers;
    // last marker clicked by the user.
    private T selectedMarker;

    MarkerLayer(LayerParent parent, Type layerType){
        super(parent, layerType);
        /*this.mapListener = new OnMapInteractionListener<T>() {
            @Override
            public void onMarkerMaximize(T marker) {

            }

            @Override
            public void onMarkerMinimize(T marker) {

            }
        };*/
        this.markers = new ArrayList<>();
        this.selectedMarker = null;
    }

    /*public abstract void updateElementEventsById(String id, ArrayList<Event> events);*/

    /*public void setMapListener(OnMapInteractionListener<T> listener){
        this.mapListener = listener;
    }*/

    public T getMarkerById(String markerId) {
        for (T marker : markers) {
            if (marker.getMarkerId().equals(markerId)) {
                return marker;
            }
        }
        return null;
    }
    public T getSelectedMarker() {
        return this.selectedMarker;
    }

    void clearAndSetMarkersFromList(List<T> otherMakers){
        this.markers.clear();
        this.markers.addAll(otherMakers);
    }

    public void setSelectedMarker(T marker) {
        this.selectedMarker = marker;
    }
    public boolean minimizeSelectedMarker(){
        if (selectedMarker != null) {
            selectedMarker.minimizeInfoWindow();
            this.selectedMarker = null;
            return true;
        }
        return false;
    }
    public void addOverlaysToOverlayList(List<Overlay> overlayList) {
        overlayList.addAll(this.markers);
    }

   /* OnMapInteractionListener<T> getMapListener() {
        return mapListener;
    }*/

    protected Drawable getDrawable(int drawable) {
        return ContextCompat.getDrawable(getMapView().getContext(), drawable);
    }
    List<T> getMarkersList() {
        return this.markers;
    }
}
