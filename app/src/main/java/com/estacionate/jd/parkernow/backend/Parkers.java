package com.estacionate.jd.parkernow.backend;

import android.support.annotation.Nullable;

import com.estacionate.jd.parkernow.server.ServerConnectionController;
import com.estacionate.jd.parkernow.server.ServerResponseListener;
import com.estacionate.jd.parkernow.server.requests.Request;
import com.estacionate.jd.parkernow.server.requests.pullRequest.GetParkersRequest;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Jorge on 26-04-2017.
 */

public class Parkers {

    public void getAllParker(){
        Request<String> request = new GetParkersRequest("0");
        ServerConnectionController.sendRequest(
                request,
                new ServerResponseListener<String>() {
                    @Override
                    public void callback(@Nullable String response) {
                        //listener.busConstructed(new Bus(service, licensePlate, response));
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
}
