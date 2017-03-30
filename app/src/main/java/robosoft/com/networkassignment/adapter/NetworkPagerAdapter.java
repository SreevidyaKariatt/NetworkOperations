package robosoft.com.networkassignment.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import robosoft.com.networkassignment.R;
import robosoft.com.networkassignment.common.JsonFragment;
import robosoft.com.networkassignment.common.MyFragment;
import robosoft.com.networkassignment.common.RecycleFragment;

/**
 * Created by sree on 14/12/15.
 */
public class NetworkPagerAdapter extends FragmentPagerAdapter{
    private String[] mTabs;
    private Context mContext;
    private FragmentManager mFm;
    public NetworkPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
        this.mFm = fm;
        mTabs = context.getResources().getStringArray(R.array.tabs);
    }

    @Override
    public Fragment getItem(int position) {

        Toast.makeText(mContext,"Position"+position,Toast.LENGTH_SHORT).show();
        if(position == 0)
        {
            JsonFragment jsonFragment = new JsonFragment();
            return  jsonFragment;
        }
        else if(position == 1){
            MyFragment myFragment = MyFragment.getInstance(position);

            return myFragment;
        }
        else if(position == 2)
        {

            RecycleFragment recycleFragment = RecycleFragment.getInstance(position);

            return  recycleFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position];
    }
}
