package com.estacionate.jd.parkernow.backend;

import android.support.annotation.Nullable;

import com.estacionate.jd.parkernow.server.ServerConnectionController;
import com.estacionate.jd.parkernow.server.ServerResponseListener;
import com.estacionate.jd.parkernow.server.requests.Request;
import com.estacionate.jd.parkernow.server.requests.pullRequest.GetParkingsRequest;

import java.util.List;

/**
 * Created by Jorge on 26-04-2017.
 */

public class Parking {

    private List<String> Parkers;
    Integer val;
    String id,name;
    Double lat,lng;

    public void getAllParker(){
        Request<String> request = new GetParkingsRequest("0");
        ServerConnectionController.sendRequest(
                request,
                new ServerResponseListener<String>() {
                    @Override
                    public void callback(@Nullable String response) {
                        //listener.busConstructed(new Bus(service, licensePlate, response));
                        setAllParker(response);
                        String[] data = response.split("&");
                        for(int i=0; i < data.length; i++)
                            System.out.println(data[i]);
                    }

                    @Override
                    public String getToken() {
                        return "";
                    }
                },
                null);
    }

    public void setAllParker(String response){
        String[] data = response.split("&");
        for(int i=0; i < data.length; i++)
            Parkers.add(data[i]);
    }

    public List<String> getAllParkers(){
        return Parkers;
    }

    public Parking(String id, Integer val, String name, Double lat, Double lng){
        this.id = id;
        this.val = val;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public Parking(){

    }

    public String getId(){
        return this.id;
    }

    public Integer getVal(){
        return this.val;
    }

    public String getName(){
        return this.name;
    }

    public Double getLat(){
        return this.lat;
    }

    public Double getLng(){
        return this.lng;
    }
}
