package com.estacionate.jd.parkernow.serviceVisibility;

import java.util.List;

/**
 * Created by Jorge on 01-05-2017.
 */
public interface ServiceVisibilityListener {
    void notifyNewState(List<String> newState);
}
