package com.estacionate.jd.parkernow.utils;

/**
 * Created by Jorge on 26-04-2017.
 */

public class Constants {
    private Constants() {
    }

    public static final String PACKAGE_NAME = "com.google.android.gms.location.activityrecognition";

    public static final String ACTIVITY_BROADCAST_ACTION = PACKAGE_NAME + ".BROADCAST_ACTION";

    public static final String ACTIVITY_EXTRA = PACKAGE_NAME + ".ACTIVITY_EXTRA";

    public static final int TWO_MINUTES = 1000 * 60 * 2;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    public static final long NOT_FAST_UPDATE_INTERVAL_IN_MILLISECONDS = 20000;
    public static final long NOT_SO_FAST_UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES";

    public static final String ACTIVITY_UPDATES_REQUESTED_KEY = PACKAGE_NAME +
            ".ACTIVITY_UPDATES_REQUESTED";

    public static final String DUMMY_LICENSE_PLATE = "DUMMYLPT";

    public static final int MIN_ACTIVITY_CONFIDENCE = 60;

    // default location
    public static final double concepcionLatitude = -36.820135;
    public static final double concepcionLongitude = -73.04439;
}
