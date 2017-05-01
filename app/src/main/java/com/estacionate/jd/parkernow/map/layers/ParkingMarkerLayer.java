package com.estacionate.jd.parkernow.map.layers;


import android.util.Log;



import com.estacionate.jd.parkernow.R;
import com.estacionate.jd.parkernow.backend.MapParking;
import com.estacionate.jd.parkernow.map.markers.ParkingMarker;
import com.estacionate.jd.parkernow.serviceVisibility.ServiceVisibilityController;
import com.estacionate.jd.parkernow.serviceVisibility.ServiceVisibilityListener;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;




/**
 * Created by Agustin Antoine on 05-05-2016.
 */
public class ParkingMarkerLayer extends MarkerLayer<ParkingMarker> {

    private ServiceVisibilityListener listener = null;
    private List<String> currentState;
    private List<MapParking> parkings;

    public ParkingMarkerLayer(
            LayerParent parent,
            List<MapParking> parkers) {
        this(parent, parkers, new ArrayList<String>());
    }

    public ParkingMarkerLayer(LayerParent parent, List<MapParking> parkers,
                              List<String> currentServiceVisibility) {
        super(parent, Type.PARKER);
        currentState = currentServiceVisibility;
        initialSetup(parkers);
    }

    public void registerServiceVisibilityListener(){
        if (listener != null) {
            ServiceVisibilityController.removeVisibilityListener();
        }
        listener = new ServiceVisibilityListener() {
            @Override
            public void notifyNewState(List<String> newState) {
                currentState = newState;
                updateMarkerList();
            }
        };
        ServiceVisibilityController.addVisibilityListener(listener);
    }

    /*private boolean isBusVisible(Parking parker) {
        return !currentState.contains(bus.getService().getName());
    }*/

    public void initialSetup(List<MapParking> parkings) {
        this.parkings = parkings;
        List<ParkingMarker> newParkingMarkerList = new ArrayList<>();
        Set<String> badParkings = new HashSet<>();
        int badBuses = 0;

        // close bus bubble from previous markers if it exists
        innerClear();

        for (MapParking parking : parkings) {
            if (parking.getLatitude() == Double.POSITIVE_INFINITY && parking.getLongitude() == Double.POSITIVE_INFINITY) {
                badParkings.add(parking.getName());
                badBuses++;
            } else {
                ParkingMarker marker = new ParkingMarker(parking, getMapView(), this,
                        getDrawable(R.drawable.parking_ic));

                newParkingMarkerList.add(marker);
                marker.minimizeInfoWindow();
            }
        }
        for (String service : badParkings) {
            Map<String, String> attributes = new HashMap<>();
            attributes.put("Service", service);
            //Log.e("Service not drawn", attributes);
        }

        this.clearAndSetMarkersFromList(newParkingMarkerList);

        /*if (badBuses >= 1) {
            Resources res = getMapView().getResources();
            String badBusesString =
                    res.getQuantityString(R.plurals.numberOfBadBuses, badBuses, badBuses);
            Toast.makeText(getMapView().getContext(), badBusesString, Toast.LENGTH_LONG).show();
        }*/

        this.setDirty(true);
    }

    private void updateMarkerList(){
        initialSetup(parkings);
        this.askForRedraw();
    }

    @Override
    public void onMarkerClick(ParkingMarker marker) {
        Log.d("Layer", "onMarkerClick");
        if (!marker.equals(getSelectedMarker())) {
            if (getSelectedMarker() != null) {
                getSelectedMarker().closeInfoWindow();
            }
            setSelectedMarker(marker);
            //getMapListener().onMarkerMaximize(marker);
            marker.maximizeInfoWindow();
        } else {
            if (getSelectedMarker().isMinimize()) {
                //getMapListener().onMarkerMaximize(marker);
                marker.maximizeInfoWindow();
            } else {
                //getMapListener().onMarkerMinimize(marker);
                marker.minimizeInfoWindow();
            }
        }
        if (marker.isMinimize()) {
            this.setSelectedMarker(null);
        }
    }

    @Override
    public void onMapDisplacement(int zoomLevel, double centerLat, double centerLon) {
        // Nothing.
    }

    @Override
    public void onUserLocationChange(GeoPoint location) {
        // Nothing.
    }

    private void innerClear() {
        for (ParkingMarker marker : getMarkersList()) {
            marker.forceCloseInfoWindow();
        }
        this.setSelectedMarker(null);
    }

    @Override
    public void clear() {
        innerClear();
        if (listener != null) {
            ServiceVisibilityController.removeVisibilityListener();
        }
    }

    /*@Override
    public void updateElementEventsById(String id, ArrayList<Event> events) {
        Log.d(TAG, "updateElementEventById: " + id);
        List<ParkingMarker> parkerMarkers = getMarkersList();
        for (ParkingMarker parkerMarker: parkerMarkers) {
            if (parkerMarker.getMarkerId().equals(id)) {
                Log.d(TAG, "found bus: " + id);
                parkerMarker.updateEvents(events);
            }
        }
    }*/

    public void setCurrentState(List<String> currentState) {
        this.currentState = currentState;
    }
}