package com.vishaan.okcupid.model.db;

import android.app.Application;
import android.net.Uri;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.vishaan.okcupid.R;
import com.vishaan.okcupid.classes.NetworkClient;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Test to ensure that network client is working as expected.
 */
public class TestNetworkClient extends ApplicationTestCase<Application> {

    private static final String TAG = TestNetworkClient.class.getSimpleName();

    public TestNetworkClient() {
        super(Application.class);
    }

    /**
     * Ensure that network data can be retrieved
     */
    public void testNetworkClient() {
        String matches = getContext().getResources().getString(R.string.url_match_page);
        Uri matchesURI = Uri.parse(matches);
        try {
            URL matchesURL = new URL(matchesURI.toString());
            String networkData = NetworkClient.getStringDataFromNetwork(matchesURL);
            assertTrue("Error: Data could not be retrieved.", networkData != null);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Malformed URL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}
