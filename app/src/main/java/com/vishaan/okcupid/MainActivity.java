package com.vishaan.okcupid;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vishaan.okcupid.fragments.GridFragment;

/**
 * Main Activity class, which displays a grid view
 * of current matches.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Main Activity Title
     */
    private final static String APP_BAR_TITLE = "Browse Matches";
    /**
     * App bar
     */
    private Toolbar mToolbar;

    /**
     * Drawer Layout
     */
    private DrawerLayout mDrawerLayout;

    /**
     * Drawer Toggle button
     */
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    /**
     * Navigation View
     */
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_grid_container);

        if (fragment == null) {
            fragment = GridFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_grid_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Initialize member variables
     */
    private void initialize() {
        //set up the toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(APP_BAR_TITLE);

        //set up the navigation drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_container);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        mNavigationView = (NavigationView) findViewById(R.id.nav_container);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                setUpNavigationActions(item);
                return true;
            }
        });
    }

    /**
     * Configure navigation menu actions
     *
     * @param item
     */
    private void setUpNavigationActions(MenuItem item) {
        //TODO: configure navigation menu actions
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_main_filter:
                //TODO: implement filter action button
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
