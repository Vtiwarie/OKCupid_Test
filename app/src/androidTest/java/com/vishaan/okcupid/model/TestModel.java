package com.vishaan.okcupid.model;

import android.app.Application;
import android.net.Uri;
import android.test.ApplicationTestCase;

import com.vishaan.okcupid.R;
import com.vishaan.okcupid.classes.NetworkClient;

import java.util.List;

/**
 * Test case for Model objects
 */
public class TestModel extends ApplicationTestCase<Application> {

    public TestModel() {
        super(Application.class);
    }

    /**
     * Test JSON parsing of users
     */
    public void testParseJSON() {
        String matches = getContext().getResources().getString(R.string.url_match_page);
        Uri matchesURI = Uri.parse(matches);
        String json = NetworkClient.getStringDataFromNetwork(matchesURI);
        List<User> users = UserList.fromJSON(json);
        assertTrue("Error: Data could not be parsed.", users != null);
    }
}
