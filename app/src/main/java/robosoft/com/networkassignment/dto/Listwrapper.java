package robosoft.com.networkassignment.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sree on 13/12/15.
 */
public class Listwrapper {

    @SerializedName("android")
    public List<ListItem> userList;
}
