package com.estacionate.jd.parkernow.map.markers;

/**
 * Created by Agustin Antoine on 29-04-2016.
 */
public interface OnLayerMarkerClickListener<T extends LayerMarker> {
    void onMarkerClick(T marker);
}
