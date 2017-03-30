package robosoft.com.networkassignment.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import robosoft.com.networkassignment.MainActivity;
import robosoft.com.networkassignment.R;
import robosoft.com.networkassignment.adapter.AndroidExpandableListAdapter;
import robosoft.com.networkassignment.dto.ListItem;
import robosoft.com.networkassignment.dto.Listwrapper;
import robosoft.com.networkassignment.manager.DownloadManager;

/**
 * Created by sree on 14/12/15.
 */
public class JsonFragment extends Fragment {

    private static HashMap<String,List<String>> mAndroidCategory = new HashMap<String,List<String>>();
   // final String TAG = "MusicSite";
    static ArrayList<String> versionList = new ArrayList<String>();
    ProgressBar mProgressBar;
    Toolbar mToolBar;
    ActionBar mActionBar;
    private String mUrl;
    private float mActionBarHeight;
    ExpandableListView exp_list;

    AndroidExpandableListAdapter adapter;
    Context context;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.json_fragment_layout, container, false);
        exp_list = (ExpandableListView) view.findViewById(R.id.exp_list);
        context = container.getContext();
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressDataList);
        mProgressBar.setVisibility(View.VISIBLE);


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null)
        {

            mProgressBar.setVisibility(View.GONE);

            adapter = new AndroidExpandableListAdapter(view.getContext(), mAndroidCategory, versionList);

            exp_list.setAdapter(adapter);

        }
        else {

            downoadData();
        }

         mActionBar = ((MainActivity)getActivity()).getSupportActionBar();
        exp_list.setOnTouchListener(new View.OnTouchListener() {
            float height;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                float height = event.getY();
                if (action == MotionEvent.ACTION_DOWN) {
                    this.height = height;
                } else if (action == MotionEvent.ACTION_UP) {
                    if (this.height < height) {
                        if (!mActionBar.isShowing()) {
                            mActionBar.show();
                        }
                        }

                    } else if (this.height > height){

                        if (mActionBar.isShowing()) {
                            mActionBar.hide();

                    }
                }
                return false;
            }
        });

    }


    private void downoadData() {
        mUrl = "http://api.learn2crack.com/android/jsonos/";
        DownloadManager.downloadData(getActivity(), mUrl, new Response.Listener<Listwrapper>() {
            @Override
            public void onResponse(Listwrapper response) {

                mProgressBar.setVisibility(View.GONE);
                if (getActivity() == null)
                    return;
                List<ListItem> listItem = response.userList;
                int size = listItem.size();

                for (int i = 0; i < size; i++) {
                    List<String> versionDetails = new ArrayList<String>();
                    ListItem item = listItem.get(i);
                    versionList.add(item.name);
                    versionDetails.add(item.ver);
                    versionDetails.add(item.api);
                    mAndroidCategory.put(item.name, versionDetails);

                }
                adapter = new AndroidExpandableListAdapter(view.getContext(), mAndroidCategory, versionList);

                exp_list.setAdapter(adapter);
                exp_list.expandGroup(0);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (getActivity() == null)
                    return;

            }
        });

    }



    }



