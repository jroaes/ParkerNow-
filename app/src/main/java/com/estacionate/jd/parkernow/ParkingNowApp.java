package com.estacionate.jd.parkernow;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jorge on 26-04-2017.
 */

public class ParkingNowApp extends Application{

    private static Context context;

    public void onCreate() {
        super.onCreate();
        ParkingNowApp.context = getApplicationContext();
    }
    public static Context getAppContext() {
        return ParkingNowApp.context;
    }
}
