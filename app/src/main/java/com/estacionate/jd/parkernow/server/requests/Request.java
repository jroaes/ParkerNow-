package com.estacionate.jd.parkernow.server.requests;

import java.util.HashMap;

/**
 * Created by Jorge on 26-04-2017.
 */


/**
 * Abstract class that defines the minimum Request logic. Requests know what to do with the
 * server response, it depends on them to pass this information to another listener.
 */
public abstract class Request<T>  {
    private String url;

    /**
     * The method that the server uses to get the URL on which the request should be done
     *
     * @return the URL with set params
     */
    public String getURL() {
        return url + getUrlParams();
    }

    /**
     * A void method to add more parameters (if necessary) to the URL
     */
    public abstract String getUrlParams();

    public Request(String url) {
        this.url = url;
    }

    public abstract T processResponse(String rawResponse);

    /**
     *
     * @return post params used by a post request
     * */
    public HashMap<String,String> getPostParams() {
        return null;
    }
}
