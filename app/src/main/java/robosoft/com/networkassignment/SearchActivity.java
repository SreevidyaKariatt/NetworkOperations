package robosoft.com.networkassignment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.SearchService;
import com.twitter.sdk.android.tweetui.TweetViewAdapter;

import java.util.List;

public class SearchActivity extends ActionBarActivity {

    private boolean mFlagLoading;
    private boolean mEndOfSearchResult;
    private static String mSearchQuery = "#ladygaga";
    private TweetViewAdapter madapter;
    private static final String mSearch_Result_Type = "recent";
    private static final int SEARCH_COUNTER = 20;
    private long mMaxId;
    ListView search_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setProgressBarIndeterminate(true);
        handleIntent(getIntent());
        madapter = new TweetViewAdapter(SearchActivity.this);
        search_list = (ListView)findViewById(R.id.tweetsearch);
        search_list.setAdapter(madapter);
        search_list.setEmptyView(findViewById(R.id.loading));
        final SearchService service = Twitter.getApiClient().getSearchService();
        service.tweets(mSearchQuery, null, null, null, mSearch_Result_Type, SEARCH_COUNTER, null, null, mMaxId, true, new Callback<Search>() {
            @Override
            public void success(Result<Search> result) {
                setProgressBarIndeterminate(false);
                final List<Tweet> tweets = result.data.tweets;
                madapter.getTweets().addAll(tweets);
                madapter.notifyDataSetChanged();
                if (tweets.size() > 0) {
                    mMaxId = tweets.get(tweets.size() - 1).id - 1;
                } else {
                    mEndOfSearchResult = true;
                }
                mFlagLoading = false;

            }

            @Override
            public void failure(TwitterException e) {
                setProgressBarIndeterminate(false);

                Toast.makeText(SearchActivity.this,"unable to load tweets",Toast.LENGTH_LONG).show();

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager manager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Enter tweets");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_search)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent)
    {
        if(intent.ACTION_SEARCH.equals(intent.getAction())){

            mSearchQuery = intent.getStringExtra(SearchManager.QUERY);

        }
    }
}
