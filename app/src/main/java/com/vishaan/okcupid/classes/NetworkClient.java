package com.vishaan.okcupid.classes;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Network client class to handle networking operations
 */
public class NetworkClient {

    /**
     * Tag used for log debugging
     */
    private static final String TAG = NetworkClient.class.getSimpleName();

    /**
     * Retrieve string data from a network, using a android.net.Uri object
     *
     * @param uri to retrieve data from
     * @return
     */
    public static String getStringDataFromNetwork(Uri uri) {
        String networkData = null;

        try {
            URL matchesURL = new URL(uri.toString());
            networkData = NetworkClient.getStringDataFromNetwork(matchesURL);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Malformed URL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return networkData;
    }
    /**
     * Retrieve string data from a network, using a java.net.URL object
     *
     * @param url to retrieve data from
     * @return String
     */
    public static String getStringDataFromNetwork(URL url) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // Create the request to the given url, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    buffer.append(line + "\n");
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            }

            if (buffer.length() == 0) {
                //stream is empty
                return null;
            }

            //return the final output string
            return buffer.toString();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        //return null by default, if string could not be retrieved
        return null;
    }
}
