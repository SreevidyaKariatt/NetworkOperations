package robosoft.com.networkassignment.adapter;


        import java.util.List;

        import android.content.Context;
        import android.graphics.Bitmap;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.TextView;


        import com.nostra13.universalimageloader.core.DisplayImageOptions;
        import com.nostra13.universalimageloader.core.ImageLoader;
        import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
        import com.nostra13.universalimageloader.core.assist.FailReason;
        import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

        import robosoft.com.networkassignment.R;
        import robosoft.com.networkassignment.dto.Music;



public class MyListViewAdapter extends ArrayAdapter<Music> {

    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;
    public MyListViewAdapter(Context ctx, int textViewResourceId, List<Music> sites) {
        super(ctx, textViewResourceId, sites);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ctx).build();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(config);
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .build();


    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        RelativeLayout row = (RelativeLayout)convertView;
        if(null == row){

            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (RelativeLayout)inflater.inflate(R.layout.row_layout,null);
        }


        final ImageView iconImg = (ImageView)row.findViewById(R.id.iconImage);
        TextView nameTxt = (TextView)row.findViewById(R.id.nameTxt);
        TextView aboutTxt = (TextView)row.findViewById(R.id.aboutTxt);
        final ProgressBar indicator = (ProgressBar)row.findViewById(R.id.progress);
        indicator.setVisibility(View.VISIBLE);
        iconImg.setVisibility(View.INVISIBLE);
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
                indicator.setVisibility(View.INVISIBLE);
                iconImg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                // TODO Auto-generated method stub

            }

        };
        mImageLoader.displayImage(getItem(pos).getThumb_url(), iconImg,mOptions, listener);
        nameTxt.setText(getItem(pos).getArtist());
        aboutTxt.setText(getItem(pos).getTitle());
        return row;


    }

}