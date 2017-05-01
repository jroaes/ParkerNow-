package com.estacionate.jd.parkernow.server;

import android.app.ProgressDialog;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.estacionate.jd.parkernow.ParkingNowApp;
import com.estacionate.jd.parkernow.server.requests.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jorge on 26-04-2017.
 */

public class ServerConnectionController {

    private ServerConnectionController(){}
    private final static String TAG = "ServerConnectionCont...";

    private static HashMap<Request,ServerResponseListener> responseListeners = new HashMap();
    private static HashMap<Request,ServerConnection.ServerErrorListener> errorListeners =
            new HashMap();

    private static Runnable runnableRequest;
    // thread to manipulate the message (server request) queue
    private final static Handler handler = new Handler();

    private static synchronized void sendInternalRequest(
            final Request request,
            final ServerResponseListener<String> listener,
            final ServerConnection.ServerErrorListener errorListener,
            final ProgressDialog dialog,
            final Boolean isPostRequest) {

        ServerConnection connection = ServerConnection.getInstance();
        connection.sendRequest(request, dialog, isPostRequest);

        // added listener to hashmap to have control when ServerConnection required an listener
        responseListeners.put(request, listener);
        errorListeners.put(request, errorListener);
    }

    /**
     *  execute the error listener assigned to the request instance with the result given by
     *  ServerConnection class
     * */
    public synchronized static void executeErrorListener(Request request, Exception error) {
        ServerConnection.ServerErrorListener listener = errorListeners.get(request);
        if (listener != null) {
            listener.notifyServerError(error);
        }

        responseListeners.remove(request);
        errorListeners.remove(request);
    }

    /**
     *  execute the response listener assigned to the request instance with the result given by
     *  ServerConnection class
     * */
    public synchronized static void executeResponseListener(Request request, String result) {
        ServerResponseListener<String> listener = responseListeners.get(request);
        if (listener == null) {
            Map<String, String> attributes = new HashMap<>();
            attributes.put("Result", result);
        } else {
            listener.callback(result);
        }

        responseListeners.remove(request);
        errorListeners.remove(request);
    }

    /**
     * Send a get request without specify a progressDialog
     */
    public static synchronized <T> void sendRequest(
            final Request<T> request,
            final ServerResponseListener<T> listener) {
        sendInternalRequest(
                request,
                new ServerResponseListener<String>() {
                    @Override
                    public void callback(String response) {
                        listener.callback(request.processResponse(response));
                    }
                    @Override
                    public String getToken() {return listener.getToken();}
                },
                null,
                null,
                false);
    }

    /**
     * Send a request by get specify a progressDialog
     */
    public static synchronized <T> void sendRequest(
            final Request<T> request,
            final ServerResponseListener<T> listener,
            final ProgressDialog dialog) {

        sendInternalRequest(
                request,
                new ServerResponseListener<String>() {
                    @Override
                    public void callback(String response) {
                        listener.callback(request.processResponse(response));
                    }
                    @Override
                    public String getToken() {return listener.getToken();}
                },
                null,
                dialog,
                false);
    }

    /**
     * Send a request by post specifying a progressDialog
     */
    public static synchronized <T> void sendPostRequest(
            final Request<T> request,
            final ServerResponseListener<T> listener,
            final ServerConnection.ServerErrorListener errorListener,
            final ProgressDialog dialog) {

        sendInternalRequest(
                request,
                new ServerResponseListener<String>() {
                    @Override
                    public void callback(String response) {
                        listener.callback(request.processResponse(response));
                    }
                    @Override
                    public String getToken() {return listener.getToken();}
                },
                errorListener,
                dialog,
                true);
    }


    /**
     * Add an initial wait time before to send a request
     *
     * @param request
     * @param listener
     * @param errorListener instance who act when an error has ocurred
     * @param delay         time to wait for next request
     * @param message       message printed on screen when map is updated
     */
    public static synchronized <T> void sendPeriodicRequestWithDelay(
            final Request<T> request,
            final ServerResponseListener<T> listener,
            final ServerConnection.ServerErrorListener errorListener,
            final long delay,
            final String message) {
        Log.d(TAG, "SendRequestWithDelay");

        runnableRequest = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "SendRequestWithDelay (" + delay + " miliseg): " + request.getURL());
                sendPeriodicRequest(request, listener, errorListener, delay, message);
            }
        };
        handler.postDelayed(runnableRequest, delay);
    }

    /**
     * Send a request periodically by get.
     *
     * @param request
     * @param listener
     * @param errorListener instance who act when an error has ocurred
     * @param delay    time to wait for next request
     * @param message  message printed on screen when map is updated
     */
    private static synchronized <T> void sendPeriodicRequest(
            final Request<T> request,
            final ServerResponseListener<T> listener,
            final ServerConnection.ServerErrorListener errorListener,
            final long delay,
            final String message) {

        runnableRequest = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Moment to send a new request : " + request.getURL());
                ServerConnectionController.sendPeriodicRequest(request,
                        listener, errorListener, delay, message);
            }
        };

        sendInternalRequest(
                request,
                new ServerResponseListener<String>() {
                    @Override
                    public void callback(String response) {
                        listener.callback(request.processResponse(response));
                        if (message != null) {
                            Toast.makeText(ParkingNowApp.getAppContext(),
                                    message, Toast.LENGTH_SHORT).show();
                        }
                        handler.postDelayed(runnableRequest, delay);
                    }
                    @Override
                    public String getToken() {return listener.getToken();}
                },
                errorListener,
                null,
                false);
    }

    /**
     *  delete current periodic request
     **/
    public static void stopCurrentPeriodicRequest() {
        Log.d(TAG, "stopCurrentPeriodicRequest");
        handler.removeCallbacksAndMessages(null);
        runnableRequest = null;
        replaceListener();
    }

    /**
     * sleep the timer used to repeat a periodic request
     **/
    public static void onPauseCurrentPeriodicRequest() {
        Log.d(TAG, "onPauseCurrentPeriodicRequest");
        handler.removeCallbacksAndMessages(null);
        replaceListener();
    }

    /**
     *  To restore periodic request. For example, when activity goes to foreground
     **/
    public static void restorePeriodicRequest() {
        Log.d(TAG, "onResumePeriodicRequest");
        handler.post(runnableRequest);
    }

    private synchronized static void replaceListener() {
        Log.d(TAG, "replaceListener");

        String token = "PERIODIC_REQUEST";

        for(Map.Entry<Request, ServerResponseListener> entry : responseListeners.entrySet()) {
            Log.d(TAG, "replaceListener: we found a listener for: " +
                    entry.getKey().getURL());
            if (token.equals(entry.getValue().getToken())) {
                entry.setValue(new ServerResponseListener<String>() {
                    @Override
                    public void callback(String response) {
                        Log.d(TAG, "Nothing to do");
                    }
                    @Override
                    public String getToken() {
                        return TAG;
                    }
                });
            }
        }
    }
}
