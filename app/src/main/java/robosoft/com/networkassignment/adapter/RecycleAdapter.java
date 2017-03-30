package robosoft.com.networkassignment.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import java.util.List;

import robosoft.com.networkassignment.R;
import robosoft.com.networkassignment.common.MySingleton;
import robosoft.com.networkassignment.common.communicator;
import robosoft.com.networkassignment.dto.Music;

/**
 * Created by sree on 16/12/15.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private List<Music> mListMusic;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    communicator commReference;
    private String TAG = "myTag";
    Context mContext;

    public RecycleAdapter(Context context,List<Music> musicData)
    {
        commReference = (communicator)context;
        mInflater = LayoutInflater.from(context);
        mListMusic = musicData;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(config);
        this.mContext = context;

    }
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.customrow,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecycleAdapter.MyViewHolder holder, int position) {
        Music musicObject = mListMusic.get(position);
        holder.title.setText(musicObject.getTitle());
        holder.artist.setText(musicObject.getArtist());
        holder.progress.setVisibility(View.VISIBLE);
        holder.icon.setVisibility(View.INVISIBLE);
        ImageLoadingListener listener = new ImageLoadingListener(){
            @Override
            public void onLoadingStarted(String arg0, View arg1) {
                // TODO Auto-generated method stub

            }



            @Override
            public void onLoadingCancelled(String arg0, View arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                holder.progress.setVisibility(View.INVISIBLE);
                holder.icon.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                // TODO Auto-generated method stub

            }

        };
        mImageLoader.displayImage(musicObject.getThumb_url(),holder.icon,listener);
        //mImageLoader = MySingleton.getInstance(mContext).getImageLoader();
        //holder.icon.setImageUrl(musicObject.getThumb_url(), mImageLoader);
    }

    @Override
    public int getItemCount() {
        return mListMusic.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView title;
        TextView artist;
        ImageView icon;
        ProgressBar progress;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.textTitle);
            artist = (TextView)itemView.findViewById(R.id.textArtist);
            icon = (ImageView)itemView.findViewById(R.id.imageAlbum);
            progress = (ProgressBar)itemView.findViewById(R.id.progress);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.i(TAG, "Position clicked is:" + position);
          /*  Intent intent = new Intent(mContext, DescriptiveAlbumActivity.class);
            Music musicObject = mListMusic.get(position);
            intent.putExtra("MusicObject",musicObject);
            mContext.startActivity(intent);*/
            commReference.communicate(mListMusic.get(position));

        }
    }

    public void onItemRemove(final int adapterPosition, final RecyclerView recyclerView) {
        final Music music = mListMusic.get(adapterPosition);
        Snackbar snackbar = Snackbar
                .make(recyclerView, "DATA REMOVED", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListMusic.add(adapterPosition, music);
                        notifyItemInserted(adapterPosition);
                        recyclerView.scrollToPosition(adapterPosition);

                    }
                });
        snackbar.show();
        mListMusic.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);

    }


}
