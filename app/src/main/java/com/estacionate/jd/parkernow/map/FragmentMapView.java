package com.estacionate.jd.parkernow.map;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import org.osmdroid.views.MapView;

/**
 * Created by Jorge on 26-04-2017.
 */

public class FragmentMapView extends MapView{
    private FragmentManager fm;
    private OnLayoutFinishedListener listener;

    public FragmentMapView(Context context, FragmentManager fragmentManager) {
        super(context);
        this.fm = fragmentManager;
    }

    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r,
                            final int b) {
        super.onLayout(changed, l, t, r, b);

        if (listener != null) {
            listener.ready();
        }
    }

    public FragmentManager getFm() {
        return fm;
    }

    public void setOnLayoutListener(OnLayoutFinishedListener listener) {
        this.listener = listener;
    }

    public interface OnLayoutFinishedListener {
        void ready();
    }
}
