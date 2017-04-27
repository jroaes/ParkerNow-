package com.estacionate.jd.parkernow.server;

import android.support.annotation.Nullable;

/**
 * Interface that allows the object sending a request to the server to get the response.
 */
public interface ServerResponseListener<T> {

    /**
     * The method that receives the response from the server after sending the request.
     *
     * @param response the response given by the server
     */
    void callback(@Nullable T response);

    /**
     * Token to identify listener
     * */
    String getToken();
}
