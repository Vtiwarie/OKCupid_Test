package com.vishaan.okcupid;

import android.app.Application;
import android.net.Uri;
import android.test.ApplicationTestCase;

import com.vishaan.okcupid.classes.NetworkClient;

/**
 * Test to ensure that network client is working as expected.
 */
public class TestNetworkClient extends ApplicationTestCase<Application> {

    public TestNetworkClient() {
        super(Application.class);
    }

    /**
     * Ensure that network data can be retrieved
     */
    public void testNetworkClient() {
        String matches = getContext().getResources().getString(R.string.url_match_page);
        Uri matchesURI = Uri.parse(matches);
        String stringData = NetworkClient.getStringDataFromNetwork(matchesURI);
        assertTrue("Error: Data could not be retrieved.", stringData != null);
    }
}
