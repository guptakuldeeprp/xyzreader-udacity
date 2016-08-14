package com.example.xyzreader.remote;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RemoteEndpointUtil {
    private static final String TAG = "RemoteEndpointUtil";

    private RemoteEndpointUtil() {
    }

    public static JSONArray fetchJsonArray() {
        String itemsJson = null;
        try {
            itemsJson = fetchPlainText(Config.BASE_URL);
        } catch (IOException e) {
            Log.e(TAG, "Error fetching items JSON", e);
            return null;
        }

        // Parse JSON
        try {
            JSONTokener tokener = new JSONTokener(itemsJson);
            Object val = tokener.nextValue();
            if (!(val instanceof JSONArray)) {
                throw new JSONException("Expected JSONArray");
            }
            return (JSONArray) val;
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing items JSON", e);
        }

        return null;
    }

    static String fetchPlainText(URL url) throws IOException {
        Log.w(TAG,"Fetching from remote url: " + url);
       /* StringBuilder respBuffer = new StringBuilder();

        if(url.getProtocol().equals("https")) {
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

            try {

                BufferedReader br =
                        new BufferedReader(
                                new InputStreamReader(con.getInputStream()));

                String input;

                while ((input = br.readLine()) != null){
                    respBuffer.append(input);
                }
                br.close();

            } catch (IOException e) {
                Log.e(TAG,e.getMessage(),e);
            }
        } else if(url.getProtocol().equals("http")) {

        } else {
            Log.e(TAG,"Could not connect to remote endpoint using url: " + url);
        }
        Log.w(TAG,"Obtained responde: " + respBuffer);
        return respBuffer.toString();*/
        //OkHttpClient.Builder builder = new OkHttpClient.Builder().proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.4.78", 5010)));
        //OkHttpClient client = builder.build();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
