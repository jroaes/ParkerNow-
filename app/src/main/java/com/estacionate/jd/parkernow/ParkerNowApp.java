package com.estacionate.jd.parkernow;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jorge on 26-04-2017.
 */

public class ParkerNowApp extends Application{

    private static Context context;

    public void onCreate() {
        super.onCreate();
        ParkerNowApp.context = getApplicationContext();
    }
    public static Context getAppContext() {
        return ParkerNowApp.context;
    }
}
