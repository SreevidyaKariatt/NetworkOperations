package robosoft.com.networkassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import robosoft.com.networkassignment.common.MySingleton;
import robosoft.com.networkassignment.dto.Music;

public class DescriptiveAlbumActivity extends AppCompatActivity {

    private NetworkImageView mImageAlbum;
    private TextView mTextId;
    private TextView mTextTitle;
    private TextView mTextArtist;
    private TextView mTextDuration;
    private Toolbar mToolbar;
    ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descriptive_album);

        mToolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mImageLoader = MySingleton.getInstance(this).getImageLoader();

      mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_18dp);

       mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();


            }
        });

        mImageAlbum = (NetworkImageView)findViewById(R.id.albumImage);
        mTextId = (TextView)findViewById(R.id.albumId);
        mTextTitle = (TextView)findViewById(R.id.albumTitle);
        mTextArtist = (TextView)findViewById(R.id.albumArtist);
        mTextDuration = (TextView)findViewById(R.id.albumDuration);


        Intent intent = getIntent();
        Music musicObject = (Music)intent.getSerializableExtra("MusicObject");
        mImageAlbum.setImageUrl(musicObject.getThumb_url(), mImageLoader);
        mTextId.setText("ID : "+musicObject.getId());
        mTextTitle.setText("Title : "+musicObject.getTitle());
        mTextArtist.setText("Artist : "+musicObject.getArtist());
        mTextDuration.setText("Duration : "+musicObject.getDuration());

    }

}
