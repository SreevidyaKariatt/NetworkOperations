package robosoft.com.networkassignment.common;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import java.io.FileNotFoundException;
import java.util.List;

import robosoft.com.networkassignment.MainActivity;
import robosoft.com.networkassignment.R;
import robosoft.com.networkassignment.adapter.RecycleAdapter;
import robosoft.com.networkassignment.dto.Music;
import robosoft.com.networkassignment.manager.DownloadManager;
import robosoft.com.networkassignment.network.MusicXmlPullParser;


import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

/**
 * Created by sree on 16/12/15.
 */
public class RecycleFragment extends Fragment implements ObservableScrollViewCallbacks {

    View view;
    ActionBar mActionBar;
    Context context;
    ProgressBar mProgressBar;
    private final String object = "Music Tag";
    private ObservableRecyclerView mRecyclerView;
    private RecycleAdapter mAdapter;
    public static RecycleFragment getInstance(int position) {
        RecycleFragment myFragment = new RecycleFragment();
        return myFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recyclefragment_layout, container, false);
        context = container.getContext();
        mRecyclerView = (ObservableRecyclerView) view.findViewById(R.id.recycleList);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progressBarForRecyclerView);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionBar = ((MainActivity)getActivity()).getSupportActionBar();
        mRecyclerView.setScrollViewCallbacks(this);
        if (isNetworkAvailable()) {
            SitesDownloadTask download = new SitesDownloadTask();
            download.execute();
        } else {

            List<Music> musicList = MusicXmlPullParser.getStackSitesFromFile(getActivity());
                    mAdapter = new RecycleAdapter(context,musicList);
                    mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));



        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) view.getContext().getSystemService(view.getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

        if (mActionBar == null) {
            return;
        }
        if (scrollState == ScrollState.UP) {
            if (mActionBar.isShowing()) {
                mActionBar.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!mActionBar.isShowing()) {
                mActionBar.show();
            }
        }
    }



    private class SitesDownloadTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //Download the file
            try {

                DownloadManager.DownloadFromUrl("http://api.androidhive.info/music/music.xml", context.openFileOutput("MusicAlbum.xml", Context.MODE_PRIVATE));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            mProgressBar.setVisibility(View.GONE);
            //setup our Adapter and set it to the ListView.
            final List<Music> musicList = MusicXmlPullParser.getStackSitesFromFile(getActivity());
            mAdapter = new RecycleAdapter(context,musicList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            // init swipe to dismiss logic
            ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {


                    mAdapter.onItemRemove(viewHolder.getAdapterPosition(), mRecyclerView);

                }
            });
            swipeToDismissTouchHelper.attachToRecyclerView(mRecyclerView);


        }

    }

}
