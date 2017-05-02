package com.estacionate.jd.parkernow.backend;

import android.support.annotation.Nullable;

import com.estacionate.jd.parkernow.server.ServerConnectionController;
import com.estacionate.jd.parkernow.server.ServerResponseListener;
import com.estacionate.jd.parkernow.server.requests.Request;
import com.estacionate.jd.parkernow.server.requests.pullRequest.GetParkingsRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jorge on 02-05-2017.
 */

public class ParkingAll extends Observable{

    private List<Parking> Parkings;

    public  ParkingAll(){
        Parkings = new ArrayList<Parking>();
        Request<String> request = new GetParkingsRequest("0");
        ServerConnectionController.sendRequest(
                request,
                new ServerResponseListener<String>() {
                    @Override
                    public void callback(@Nullable String response) {
                        //listener.busConstructed(new Bus(service, licensePlate, response));
                        setAllParker(response);
                    }

                    @Override
                    public String getToken() {
                        return "";
                    }
                },
                null);
    }

    public void setAllParker(String response){
        String[] data = response.split("&"),parking_data;
        Parking parking;
        String id,name;
        Double lat,lng;
        Integer val;
        for(int i=0; i < data.length; i++){
            parking_data = data[i].split(",");
            id = parking_data[0];
            val = Integer.parseInt(parking_data[1]);
            name = parking_data[2];
            lat = Double.parseDouble(parking_data[3]);
            lng = Double.parseDouble(parking_data[4]);
            parking = new Parking(id,val,name,lat,lng);
            Parkings.add(parking);
        }
        setChanged();
        notifyObservers();
    }

    public List<Parking> getAllParkings(){
        return Parkings;
    }

}
