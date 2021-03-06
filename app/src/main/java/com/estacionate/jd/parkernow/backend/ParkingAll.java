package com.estacionate.jd.parkernow.backend;

import android.support.annotation.Nullable;

import com.estacionate.jd.parkernow.server.ServerConnectionController;
import com.estacionate.jd.parkernow.server.ServerResponseListener;
import com.estacionate.jd.parkernow.server.requests.Request;
import com.estacionate.jd.parkernow.server.requests.pullRequest.GetParkingsRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jorge on 02-05-2017.
 */

public class ParkingAll extends Observable{

    private List<Parking> Parkings;
    private final static String tag = "ParkingAll";

    public  ParkingAll(){
        Parkings = new ArrayList<Parking>();
        Request<List<Map<String,String>>> request = new GetParkingsRequest("0");
        ServerConnectionController.sendRequest(
                request,
                new ServerResponseListener<List<Map<String,String>>>() {
                    @Override
                    public void callback(@Nullable List<Map<String,String>> response) {
                        setAllParker(response);
                    }

                    @Override
                    public String getToken() {
                        return "";
                    }
                },
                null);
    }

    public void setAllParker(List<Map<String,String>> response){
        for(int i=0; i < response.size(); i++) {
            String id = response.get(i).get("id");
            Integer val = Integer.parseInt(response.get(i).get("precio"));
            String name = response.get(i).get("address");
            Double lat = Double.parseDouble(response.get(i).get("lat"));
            Double lng = Double.parseDouble(response.get(i).get("lng"));
            Parkings.add(new Parking(id, val, name, lat, lng));
        }
        setChanged();
        notifyObservers(tag);
    }

    public List<Parking> getAllParkings(){
        return Parkings;
    }

}
