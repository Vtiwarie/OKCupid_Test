package com.vishaan.okcupid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vishaan.okcupid.R;
import com.vishaan.okcupid.classes.ImageViewLoader;
import com.vishaan.okcupid.model.User;

import java.util.List;
import java.util.Random;

/**
 * Adapter to update the GridFragment UI
 */
public class GridFragmentAdapter extends RecyclerView.Adapter<GridFragmentViewHolder> {

    /**
     * List of Users to display in grid view
     */
    protected List<User> mUsers;
    private Context mContext;

    public GridFragmentAdapter(List<User> users, Context context) {

        mUsers = users;
        mContext = context;
    }

    @Override
    public GridFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_grid_fragment, parent, false);
        return new GridFragmentViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(GridFragmentViewHolder holder, int position) {
        holder.bindUser(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setUsers(List<User> users) {
        mUsers = users;
    }

}

/**
 * View Holder class to draw the UI to the
 */
class GridFragmentViewHolder extends RecyclerView.ViewHolder {
    private TextView mUsername;
    private TextView mAge;
    private TextView mCity;
    private TextView mMatchPercentage;
    private ImageView mPhoto;
    private Context mContext;

    public GridFragmentViewHolder(View view, Context context) {
        super(view);
        mContext = context;
        mUsername = (TextView) view.findViewById(R.id.txt_username);
        mAge = (TextView) view.findViewById(R.id.txt_age);
        mCity = (TextView) view.findViewById(R.id.txt_city);
        mMatchPercentage = (TextView) view.findViewById(R.id.txt_match_percentage);
        mPhoto = (ImageView) view.findViewById(R.id.photo_main);
    }

    /**
     * Bind the user data to the recycler list item
     *
     * @param user
     */
    public void bindUser(User user) {
        mUsername.setText(user.getUsername());
        mAge.setText(String.valueOf(user.getAge()));
        mCity.setText(user.getLocation().city_name);
        Random random = new Random();
        //wasn't sure how to calculate match percentage, so random given
        int matchPercentage = random.nextInt(100);
        mMatchPercentage.setText(matchPercentage + "%");
        //load images. First check cache, if there are no cached images,
        //then we attempt to load from the web
        ImageViewLoader imgLoader = new ImageViewLoader(mContext);
        imgLoader.DisplayImage(user.getPhoto().full_paths.original, mPhoto);
    }
}