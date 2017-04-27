package com.estacionate.jd.parkernow.server.requests.pullRequest;

import android.support.v4.util.Pair;
import android.util.Log;

import com.estacionate.jd.parkernow.server.requests.Request;
import com.estacionate.jd.parkernow.server.requests.RequestUrlMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;


/**
 * A class extending ARequest, specifically used to request a list of Buses given a BusStop
 */
public class GetParkersRequest extends Request {
    private String id;

    /**
     * The constructor of GetBusesRequest
     *
     * @param pId The Id of the Parkers to be requested
     */
    public GetParkersRequest(
            String pId) {
        super(RequestUrlMapper.getUrlFor(RequestUrlMapper.GET_PARKER_METHOD));
        this.id = pId;
    }

//    /**
//     * Method used to send the request to the server.
//     * The response is received by the processResponse method
//     */
//    public void getNearbyBuses() {
//        ServerConnection instance = ServerConnection.getInstance();
//        //instance.sendRequest(this, this.progressDialog);
//    }


    public String processResponse(String pResponse) {
        try {
            String data = "";

            JSONArray response = new JSONArray(pResponse);
            String precio;
            String dir;
            String[] latlng;
            for(int i=0; i < response.length(); i++){
                JSONObject response_object = response.getJSONObject(i);
                precio = String.valueOf(response_object.getInt("precio"));
                JSONObject ubicacion = response_object.getJSONObject("ubicacion");
                dir = ubicacion.getString("direccion");
                latlng = ubicacion.getString("coordenadas").split("\\[|\\]|\\,");
                data += precio + "," + dir + "," + latlng[1] + "," + latlng[2];
                if(!((i+1) >= response.length())){
                    data += "&";
                }
            }


            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("GetBusesRequest", pResponse);
        }
        return null;
    }

    @Override
    public String getUrlParams() {
        return "";
    }
}
