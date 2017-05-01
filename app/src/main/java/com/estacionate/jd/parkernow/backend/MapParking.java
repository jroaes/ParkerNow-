package com.estacionate.jd.parkernow.backend;

/**
 * Created by Jorge on 01-05-2017.
 */

public class MapParking extends Parking {

    Double latitude;
    Double longitude;

    public MapParking(){
        latitude = Double.POSITIVE_INFINITY;
        longitude = Double.POSITIVE_INFINITY;
    }

    public MapParking(String parker_data){
        String[] line = parker_data.split("&");
        for(int i=0; i < line.length; i++){
            String[] data = line[i].split(",");
            for(int j=0; j < data.length; j++){
                this.latitude = Double.parseDouble(data[2]);
                this.longitude = Double.parseDouble(data[3]);
            }
        }

    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
