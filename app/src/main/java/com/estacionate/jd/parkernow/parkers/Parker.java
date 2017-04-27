package com.estacionate.jd.parkernow.parkers;

/**
 * Created by Jorge on 27-04-2017.
 */

public class Parker {

    Integer val;
    String id,name;
    Double lat,lng;

    public Parker(String id, Integer val, String name, Double lat, Double lng){
        this.id = id;
        this.val = val;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
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
