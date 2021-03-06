package com.estacionate.jd.parkernow.server.requests.pullRequest;

import android.util.ArrayMap;
import android.util.Log;

import com.estacionate.jd.parkernow.server.requests.Request;
import com.estacionate.jd.parkernow.server.requests.RequestUrlMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A class extending ARequest, specifically used to request a list of Buses given a BusStop
 */
public class GetParkingsRequest extends Request {
    private String id;

    /**
     * The constructor of GetBusesRequest
     *
     * @param pId The Id of the Parking to be requested
     */
    public GetParkingsRequest(
            String pId) {
        super(RequestUrlMapper.getUrlFor(RequestUrlMapper.GET_PARKER_METHOD));
        this.id = pId;
    }


    public List<Map<String,String>> processResponse(String pResponse) {
        try {
            List<Map<String,String>> list = new ArrayList<>();
            JSONArray response = new JSONArray(pResponse);
            String precio;
            String dir;
            String[] latlng;
            String id;
            for (int i = 0; i < response.length(); i++) {
                Map<String,String> map = new ArrayMap<String,String>();
                JSONObject response_object = response.getJSONObject(i);
                precio = String.valueOf(response_object.getInt("precio"));
                id = response_object.getString("_id");
                JSONObject ubicacion = response_object.getJSONObject("location");
                dir = ubicacion.getString("address");
                latlng = ubicacion.getString("coordinates").split("\\[|\\]|\\,");
                map.put("precio",precio);
                map.put("id",id);
                map.put("address",dir);
                map.put("lat",latlng[1]);
                map.put("lng",latlng[2]);
                list.add(map);
            }


            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("GetParkingsRequest", pResponse);
        }
        return null;
    }

    @Override
    public String getUrlParams() {
        return "";
    }
}
