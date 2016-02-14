package com.vishaan.okcupid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vishaan.okcupid.R;

/**
 * Display a fragment to display bookmarks
 */
public class BookmarksFragment extends Fragment {

    public BookmarksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters if given.
     *
     * @return A new instance of fragment BookmarksFragment.
     */
    public static BookmarksFragment newInstance() {
        return new BookmarksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        return view;
    }
}
