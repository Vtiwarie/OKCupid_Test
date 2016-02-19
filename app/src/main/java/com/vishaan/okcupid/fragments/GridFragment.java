package com.vishaan.okcupid.fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.vishaan.okcupid.R;
import com.vishaan.okcupid.adapters.GridFragmentAdapter;
import com.vishaan.okcupid.classes.NetworkClient;
import com.vishaan.okcupid.model.User;
import com.vishaan.okcupid.model.UserList;
import com.vishaan.okcupid.model.db.UserDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Display a grid view fragment of Users
 */
public class GridFragment extends Fragment {

    /**
     * Tag used for debugging
     */
    private static final String TAG = GridFragment.class.getSimpleName();

    /**
     * Recycler view object
     */
    private RecyclerView mRecyclerView;

    /**
     * Swipe to refresh layout
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * Reference to the "No connection" layout
     */
    private FrameLayout mNoConntectionLayout;

    /**
     * Recycler view adapter to control grid view presentation
     */
    private GridFragmentAdapter mAdapter;

    /**
     * Grid layout manager
     */
    private GridLayoutManager mGridLayoutManager;

    /**
     * List of users for display in grid
     */
    private List<User> mUsers;

    /**
     * Object to communicate with user table
     */
    private UserDataSource mUserDataSource;

    public GridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters if given.
     *
     * @return A new instance of fragment GridFragment.
     */
    public static GridFragment newInstance() {
        return new GridFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mUsers = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid, container, false);

        //set up database handle and open connection
        mUserDataSource = new UserDataSource(view.getContext());
        mUserDataSource.open();

        //Grab a reference to the "No connected" frame layout to make it disappear/reappear depending on internet connection
        mNoConntectionLayout = (FrameLayout) view.findViewById(R.id.frame_no_network_container);

        //set up recycler view
        int spanCount = view.getContext().getResources().getInteger(R.integer.grid_span_count);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_grid_container);
        mGridLayoutManager = new GridLayoutManager(inflater.getContext(), spanCount);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        //set up swipe-2-refresh layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                fetchDataFromWeb();
            }
        });

        //set up the initial UI
        updateUI();

        return view;
    }

    /**
     * If the users is connected to the network, attempt to grab fresh data
     * whenever the fragment is resumed.
     */
    @Override
    public void onResume() {
        super.onResume();
        mUserDataSource.open();
        fetchDataFromWeb();
    }

    @Override
    public void onPause() {
        super.onPause();
        mUserDataSource.close();
    }

    /**
     * Fetch fresh content from the web using
     * an asynctask
     */
    private void fetchDataFromWeb() {
        FetchUsersTask fetchUsersTask = new FetchUsersTask();
        fetchUsersTask.execute();
    }

    /**
     * Redraw the UI
     */
    private void updateUI() {
        if (mUsers.isEmpty()) {
            //if there are no users set for display, attempt to retrieve from database cache
            mUsers = mUserDataSource.getUsers();
            mAdapter = new GridFragmentAdapter(mUsers, getContext());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            //notify that the adapter data set has changed and the UI should redraw itself
            mAdapter.setUsers(mUsers);
            mAdapter.notifyDataSetChanged();
        }

        if(mUsers.isEmpty()) {
            //set the "No Connection Found" field to visible if no data can be retrieved
            //neither from database cache nor web
            mNoConntectionLayout.setVisibility(View.VISIBLE);
        } else {
            //remove the field when data is found
            mNoConntectionLayout.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Asynctask to fetch fresh content from web API
     */
    private class FetchUsersTask extends AsyncTask<Void, Void, List<User>> {

        @Override
        protected List<User> doInBackground(Void... params) {
            List<User> users = new ArrayList<>();
            String matches = getContext().getResources().getString(R.string.url_match_page);
            Uri matchesURI = Uri.parse(matches);
            try {
                String json = NetworkClient.getStringDataFromNetwork(matchesURI);
                if(json == null || json.equals("")) {
                    return users;
                }
                users = UserList.fromJSON(json);
                //save users to database
                mUserDataSource.bulkInsert(users);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
                e.printStackTrace();
            }

            return users;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            //if data was returned from the web, use it to update the UI,
            //otherwise, settle for the cached data that was loaded previously
            if(users != null && !users.isEmpty()) {
                mUsers = users;
                updateUI();
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
