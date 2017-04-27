package com.estacionate.jd.parkernow.server;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.estacionate.jd.parkernow.server.requests.Request;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by Jorge on 26-04-2017.
 */

public class ServerConnection {
    private static ServerConnection instance = new ServerConnection();
    private ArrayList<ServerErrorListener> errorListeners = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();

    private final static String TAG = "ServerConnection";

    /**
     * Get the server instance
     *
     * @return the server
     */
    public static ServerConnection getInstance() {
        return instance;
    }

    private ServerConnection() {
    }

    public void addErrorListener(ServerErrorListener listener) {
        this.errorListeners.add(listener);
    }

    public void removeErrorListener(ServerErrorListener listener) {
        this.errorListeners.remove(listener);
    }

    /**
     * The method that creates an AsyncTask that sends the request by get.
     *
     * @param request        The object that contains the data of the request, including URL and params.
     * @param progressDialog progressDialog used by the ServerAsyncTask while loading data
     *                       It can be null
     * @param isPostRequest  indicate if request has to be sent by post
     */
    public void sendRequest(
            final Request request,
            final ProgressDialog progressDialog,
            final Boolean isPostRequest) {

        new ServerAsyncTask(request, progressDialog,
                isPostRequest).execute();
    }

    public interface ServerErrorListener {
        void notifyServerError(Exception e);
    }

    class ServerAsyncTask extends AsyncTask<Void, Integer, String> {
        private Exception error;
        private boolean isPostRequest;
        private Request request;
        private ProgressDialog progressDialog;

        public ServerAsyncTask(Request request,
                               ProgressDialog progressDialog,
                               Boolean isPostRequest) {

            this.isPostRequest = isPostRequest;
            this.request = request;
            this.progressDialog = progressDialog;
        }

        @Override
        protected void onPreExecute() {
            if (progressDialog != null) {
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        ServerAsyncTask.this.cancel(true);
                    }
                });

                progressDialog.setProgress(0);
                progressDialog.show();
            }
        }

        @Override
        public String doInBackground(Void... params) {
            okhttp3.Request okhttpRequest;
            okhttp3.Response okhttpResponse;
            try {
                String rawUrl = request.getURL().replace("+", "%20");
                URL url = new URL(rawUrl);
                Log.d("URL", rawUrl);

                okhttp3.Request.Builder builder = new okhttp3.Request.Builder()
                        .url(url);

                if (isPostRequest) {
                    Log.d(TAG, "it is a post request");
                    okhttpRequest = builder.post(/*postURL*/null)
                            .build();
                    //is get request
                } else {
                    okhttpRequest = builder
                            .build();
                }

                try {
                    okhttpResponse = client.newCall(okhttpRequest).execute();
                } catch (Exception e) {
                    //TODO: Delete when floating ip in the new server is available
                    okhttpResponse = null;
                }

                if (!okhttpResponse.isSuccessful()) {
                    Log.d(TAG, "responseCode: " + okhttpResponse.code());
                }

                return okhttpResponse.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                error = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (result == null) {
                // notify server controller to execute errorLister assigned to request
                ServerConnectionController.executeErrorListener(request, error);

                // notify observers
                for (ServerErrorListener listener : errorListeners) {
                    listener.notifyServerError(error);
                }
            } else {
                // notify controller to execute the listener assigned to request
                ServerConnectionController.executeResponseListener(request, result);
            }
        }

        /**
         * Method that transforms a HashMap into a useful object for sending data by post
         *
         * @param params The Hashmaps with the post params
         * @return Object with the correct format
         */
        private RequestBody getPostData(HashMap<String,String> params) {

            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            String key, value;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                builder.addFormDataPart(key, value);
            }

            return builder.build();
        }


    }
}
