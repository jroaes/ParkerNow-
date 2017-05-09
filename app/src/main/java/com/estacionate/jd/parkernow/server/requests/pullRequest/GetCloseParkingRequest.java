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
 * Created by Jorge on 08-05-2017.
 */

public class GetCloseParkingRequest extends Request {

    private String UrlParams;

    public GetCloseParkingRequest(Double lat, Double lng){
        super(RequestUrlMapper.getUrlFor(RequestUrlMapper.GET_PARKER_WITH_PARAMS_METHOD));
        this.UrlParams = "?lat="+lat.toString()+"&lng="+lng.toString();
    }

    @Override
    public String getUrlParams() {
        return UrlParams;
    }

    @Override
    public List<Map<String,String>> processResponse(String rawResponse) {

        try{
            List<Map<String,String>> list = new ArrayList<>();
            String precio,dir,id,distance;
            String[] latlng;
            JSONArray response = new JSONArray(rawResponse);
            for (int i = 0; i < response.length(); i++) {
                Map<String,String> map = new ArrayMap<String,String>();
                JSONObject response_obj = response.getJSONObject(i);
                JSONObject obj = response_obj.getJSONObject("obj");
                precio = String.valueOf(obj.getInt("precio"));
                id = obj.getString("_id");
                JSONObject ubicacion = obj.getJSONObject("location");
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
            Log.e("GetCloseParkingRequest", rawResponse);
        }
        return null;
    }
}
