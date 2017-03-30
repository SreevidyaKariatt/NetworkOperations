package robosoft.com.networkassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Toast;

import robosoft.com.networkassignment.adapter.NetworkPagerAdapter;
import robosoft.com.networkassignment.dto.Music;
import robosoft.com.networkassignment.tabs.SlidingTabLayout;
import robosoft.com.networkassignment.common.communicator;

public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnScrollChangedListener,communicator{

    private Toolbar mToolbar;
    private float mActionBarHeight;
    private ActionBar mActionBar;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        mToolbar = (Toolbar)findViewById(R.id.app_bar);

        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.tabs);

        mViewPager = (ViewPager)findViewById(R.id.pager);

       setSupportActionBar(mToolbar);
      //  mActionBar = getSupportActionBar();
       getSupportActionBar().setDisplayShowHomeEnabled(true);
        mViewPager.setAdapter(new NetworkPagerAdapter(getSupportFragmentManager(), this));
        mSlidingTabLayout.setViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present../
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.twitterid:
                Toast.makeText(this,"Share via twitter",Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(this,TwitterLoginActivity.class);
               // startActivity(intent);
                Intent intent = new Intent(this,ShareWithTwitterActivity.class);
                startActivity(intent);
                break;



        }
        return super.onOptionsItemSelected(item);
        //noinspection SimplifiableIfStatement


    }

    @Override
    public void onScrollChanged() {

    }

    @Override
    public void communicate(Music musicObject) {
        Intent intent = new Intent(MainActivity.this, DescriptiveAlbumActivity.class);
        intent.putExtra("MusicObject",musicObject);
        startActivity(intent);
    }
}
