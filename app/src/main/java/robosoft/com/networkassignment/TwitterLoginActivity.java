package robosoft.com.networkassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class TwitterLoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    TwitterLoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_login);
        Toast.makeText(this, "Inside twitterloginactivity", Toast.LENGTH_LONG).show();

        mToolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        //  mActionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        loginButton = (TwitterLoginButton)findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                String username = result.data.getUserName();
         Intent intent = new Intent(TwitterLoginActivity.this,SearchActivity.class);
              startActivity(intent);
                Toast.makeText(TwitterLoginActivity.this, username, Toast.LENGTH_LONG).show();
             //   Intent intent = new Intent(TwitterLoginActivity.this,ShareWithTwitterActivity.class);

               // startActivity(intent);


            }

            @Override
            public void failure(TwitterException e) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode,resultCode,data);
    }
}
