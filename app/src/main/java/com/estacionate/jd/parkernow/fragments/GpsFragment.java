package com.estacionate.jd.parkernow.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.estacionate.jd.parkernow.R;


/**
 * Created by Jorge on 02-05-2017.
 */

public class GpsFragment extends Fragment {

    Fragment gpsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gpsFragment = this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View createdView;
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.gps_offline, container, false);
        createdView = relativeLayout;



        return createdView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Button accept = (Button) getView().findViewById(R.id.accept_gps);
        Button decline = (Button) getView().findViewById(R.id.decline_gps);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().remove(gpsFragment).commit();
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().remove(gpsFragment).commit();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}



