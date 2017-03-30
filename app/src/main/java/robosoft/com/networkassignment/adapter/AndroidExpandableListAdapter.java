package robosoft.com.networkassignment.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;
import robosoft.com.networkassignment.R;


/**
 * Created by sree on 15/12/15.
 */
public class AndroidExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mCtx;
    private HashMap<String,List<String>> mAndroidCategory;
    private List<String>    mAndroidList;


    public AndroidExpandableListAdapter(Context context, HashMap<String, List<String>> androidCategory, List<String> androidList)
    {
        this.mCtx = context;
        this.mAndroidCategory = androidCategory;
        this.mAndroidList = androidList;
    }
    @Override
    public int getGroupCount() {
        return mAndroidList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mAndroidCategory.get(mAndroidList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mAndroidList.get(groupPosition);
    }

    @Override
    public Object getChild(int parent, int child) {
        return mAndroidCategory.get(mAndroidList.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {
        String group_title = (String)getGroup(parent);
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_json_layout,parentView,false);
        }
        TextView parentText = (TextView)convertView.findViewById(R.id.txtParentJson);
        parentText.setTypeface(null, Typeface.BOLD);
        parentText.setText(group_title);
        return convertView;
    }

    @Override
    public View getChildView(int parent, int child, boolean isLastChild, View convertView, ViewGroup parentView) {



        String child_title = (String)getChild(parent,child);
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_json_layout,parentView,false);
        }
        TextView childTextView = (TextView)convertView.findViewById(R.id.txtChildJson);
        childTextView.setText(child_title);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
