package com.estacionate.jd.parkernow.server.requests;



import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Helper class to contain all the server hardcoded URLs. If by any reason one of this URLs change
 * just modify the respective value of the map to propagate the change to the requests.
 */
public class RequestUrlMapper {
    // Base URL of the server.
    private final static String BASE_URL_PROD = "https://parking-server-conce.herokuapp.com";
    private final static String BASE_URL_DEV = "https://parking-server-conce.herokuapp.com";

    // Map for the requests URLs.
    private static final Map<String, String> URL_MAP;

    public static final String GET_PARKER_METHOD = "estacionamientos";


    static {
        Map<String, String> urlMap = new HashMap<>();
        urlMap.put(GET_PARKER_METHOD, "/api/estacionamientos/");
        URL_MAP = Collections.unmodifiableMap(urlMap);
    }

    /**
     * Obtain the url of a request given its method.
     * @param method Name of the request in the Map.
     * @return The url for this request.
     */
    public static String getUrlFor(String method) {
        return BASE_URL_PROD + URL_MAP.get(method);
    }

}
