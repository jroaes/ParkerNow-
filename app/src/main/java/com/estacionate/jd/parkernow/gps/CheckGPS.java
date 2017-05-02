package com.estacionate.jd.parkernow.gps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.estacionate.jd.parkernow.R;
import com.estacionate.jd.parkernow.fragments.GpsFragment;
import com.estacionate.jd.parkernow.fragments.ReportMapFragment;

/**
 * Created by Jorge on 02-05-2017.
 */

public class CheckGPS {

    private static Context context;
    private FragmentManager fm;

    public CheckGPS(Context context, FragmentManager fm){
        this.context = context;
        this.fm = fm;
        initiallize();
    }

    public void initiallize(){
        final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }


    }

    private void buildAlertMessageNoGps() {
        GpsFragment gpsFragment = new GpsFragment();
        fm.beginTransaction().add(R.id.container_gps, gpsFragment).commit();
        fm.executePendingTransactions();
        /*final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();*/
    }
}
