package com.estacionate.jd.parkernow.serviceVisibility;

import android.content.Context;
import android.util.Log;

import com.estacionate.jd.parkernow.backend.Parking;

import java.util.List;


/**
 * Created by Jorge on 01-05-2017.
 */
public class ServiceVisibilityController {

    private static ServiceVisibilityListener listener = null;
    private static List<String> cachedMap;
    private static String cachedBusStopCode;

    public static List<String> getStateForServices(
            Context context, String busStop) {

        if (cachedBusStopCode != null && cachedBusStopCode.equals(busStop)) {
            return cachedMap;
        }

        /*HashMap<String, VisibilityState> map = new HashMap<>();
        for (Service service : services) {
            if (cachedMap.contains(service.getServiceName())) {
                map.put(service.getServiceName(), VisibilityState.INVISIBLE);
            } else {
                map.put(service.getServiceName(), VisibilityState.VISIBLE);
            }
        }*/
        Parking parking = new Parking();
        cachedMap = parking.getAllParkers();
        cachedBusStopCode = busStop;
        return cachedMap;
    }


    public static void notifyNewState(List<String> newState) {
        Log.d("ServiceVisibility", "notifyNewState");
        if (listener != null) {
            listener.notifyNewState(newState);
        }
    }

    public static void addVisibilityListener(ServiceVisibilityListener newListener){
        listener = newListener;
    }

    public static void removeVisibilityListener(){
        listener = null;
    }

}
