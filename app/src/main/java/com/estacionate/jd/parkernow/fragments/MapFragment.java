package com.estacionate.jd.parkernow.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.estacionate.jd.parkernow.map.FragmentMapView;
import com.estacionate.jd.parkernow.utils.Constants;

import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.MapBoxTileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/**
 * Created by Jorge on 26-04-2017.
 */

public abstract class MapFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 5216;
    // Initial Zoom Value
    protected final int initialZoom = 18;
    protected MapView mapView;

    protected GeoPoint currentPosition = null;

    protected int getMapLayoutId() {
        return -1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        initializeMap(Constants.concepcionLatitude, Constants.concepcionLongitude);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View createdView;

        currentPosition = new GeoPoint(Constants.concepcionLatitude, Constants.concepcionLongitude);

        if (getMapLayoutId() == -1) {
            LinearLayout linearLayout =
                    (LinearLayout) inflater.inflate(getLayoutId(), container, false);
            linearLayout.addView(this.mapView);
            createdView = linearLayout;
        } else {
            createdView = inflater.inflate(getLayoutId(), container, false);
            LinearLayout mapLayout = (LinearLayout) createdView.findViewById(getMapLayoutId());
            mapLayout.addView(this.mapView);
        }


        this.mapView.setMapListener(
                new DelayedMapListener(
                        new MapListener() {
                            Integer x, y;

                            public boolean onZoom(final ZoomEvent zoomEvent) {
                                Log.i(TAG, "onZoom: " + zoomEvent.getZoomLevel());
                                return true;
                            }

                            public boolean onScroll(final ScrollEvent e) {
                                Log.d(TAG, "onScroll");
                                MapFragment.this.onScroll();
                                if (x == null || y == null) {
                                    x = e.getX();
                                    y = e.getY();
                                } else if (x == e.getX() && y == e.getY()) {
                                    return true;
                                }



                                return true;
                            }
                        }, 200));

        return createdView;
    }

    protected abstract int getLayoutId();

    protected void onScroll() {}
    protected void onFakeScroll() {}

    /**
     * Initialize the map_layout with default behavior center and zoom in the given location
     *
     * @param pInitLat   latitude of the center of the map_layout
     * @param pInitLon   longitude of the center of the map_layout
     */
    private void initializeMap(double pInitLat, double pInitLon) {
        Log.d(TAG, "initializeMap");
        /* Get MapView object and set its layout parameters */
        //this.mapView = (MapView) getContext().getApplicationContext().getResources().get(R.id.map);
        this.mapView = new FragmentMapView(this.getContext(), this.getFragmentManager());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        this.mapView.setLayoutParams(params);

        /* Set map_layout style */
        /*final MapBoxTileSource tileSource = new MapBoxTileSource("mapbox", 1, 20, 256, ".png");
        tileSource.retrieveAccessToken(this.getContext());
        tileSource.retrieveMapBoxMapId(this.getContext());
        this.mapView.setTileSource(tileSource);*/
        this.mapView.setTileSource(TileSourceFactory.MAPNIK);

        /* Set map_layout controls */
        this.mapView.setMultiTouchControls(true);

        this.mapView.getController().setCenter(new GeoPoint(pInitLat, pInitLon));
        this.mapView.getController().setZoom(initialZoom);
        this.mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    public void moveAndCenterIn(Double lat, Double lon) {
        onFakeScroll();
        mapView.getController().setCenter(new GeoPoint(lat, lon));
        mapView.getController().setZoom(initialZoom);
    }

    public void moveCenterInAndOpen(Double lat, Double lng, String busStopId) {
        moveAndCenterIn(lat, lng);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
