package com.estacionate.jd.parkernow.backend;

import android.support.annotation.Nullable;
import android.util.Log;

import com.estacionate.jd.parkernow.server.ServerConnectionController;
import com.estacionate.jd.parkernow.server.ServerResponseListener;
import com.estacionate.jd.parkernow.server.requests.Request;
import com.estacionate.jd.parkernow.server.requests.pullRequest.GetCloseParkingRequest;
import com.estacionate.jd.parkernow.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jorge on 08-05-2017.
 */

public class ParkingClose {

    private Double lat, lng;
    private List<Parking> parkingList;
    private final static String tag = "ParkingClose";

    public ParkingClose(Double lat, Double lng){
        this.lat = lat;
        this.lng = lng;
        parkingList = new ArrayList<Parking>();
        initiallize();
    }

    private void initiallize(){
        Request<List<Map<String,String>>> request = new GetCloseParkingRequest(Constants.concepcionLatitude, Constants.concepcionLongitude);
        ServerConnectionController.sendRequest(
                request,
                new ServerResponseListener<List<Map<String,String>>>() {
                    @Override
                    public void callback(@Nullable List<Map<String,String>> response) {
                        setParker(response);
                    }

                    @Override
                    public String getToken() {
                        return "";
                    }
                },
                null);
    }

    private void setParker(List<Map<String,String>> response){
        for(int i=0; i < response.size(); i++) {
            String id = response.get(i).get("id");
            Integer val = Integer.parseInt(response.get(i).get("precio"));
            String name = response.get(i).get("address");
            Double lat = Double.parseDouble(response.get(i).get("lat"));
            Double lng = Double.parseDouble(response.get(i).get("lng"));
            parkingList.add(new Parking(id, val, name, lat, lng));
        }
    }

    public List<Parking> getParkingList(){
        return parkingList;
    }
}
