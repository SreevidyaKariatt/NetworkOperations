package robosoft.com.networkassignment.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;

import robosoft.com.networkassignment.R;
import robosoft.com.networkassignment.adapter.MyListViewAdapter;
import robosoft.com.networkassignment.manager.DownloadManager;
import robosoft.com.networkassignment.network.MusicXmlPullParser;


/**
 * Created by sree on 14/12/15.
 */
public class MyFragment extends Fragment {
    View view;
    Context context;
    private TextView mTextView;
    private ListView mListView;
    private MyListViewAdapter mRecycleAdapter;

    public static MyFragment getInstance(int position) {
        MyFragment myFragment = new MyFragment();
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one_layout, container, false);
        context = container.getContext();
        mListView = (ListView) view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isNetworkAvailable()) {
            SitesDownloadTask download = new SitesDownloadTask();
            download.execute();
        } else {
            mRecycleAdapter = new MyListViewAdapter(view.getContext(), -1, MusicXmlPullParser.getStackSitesFromFile(getActivity()));
            mListView.setAdapter(mRecycleAdapter);
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) view.getContext().getSystemService(view.getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private class SitesDownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                DownloadManager.DownloadFromUrl("http://api.androidhive.info/music/music.xml", context.openFileOutput("StackSites.xml", Context.MODE_PRIVATE));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result){

            mRecycleAdapter = new MyListViewAdapter(view.getContext(), -1,  MusicXmlPullParser.getStackSitesFromFile(view.getContext()));
            mListView.setAdapter(mRecycleAdapter);

        }

    }

}
