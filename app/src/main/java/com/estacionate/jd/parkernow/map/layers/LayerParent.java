package com.estacionate.jd.parkernow.map.layers;


import com.estacionate.jd.parkernow.map.FragmentMapView;

/**
 * Created by Jorge on 01-05-2017.
 */
public interface LayerParent {
    FragmentMapView getMapViewInstance();
    void askForRedraw();
}
