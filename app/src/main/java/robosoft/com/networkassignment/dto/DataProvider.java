package robosoft.com.networkassignment.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sree on 14/12/15.
 */
public class DataProvider {

    public static HashMap<String,List<String>> getInfo()
    {
        HashMap<String,List<String>> versionDetails = new HashMap<String,List<String>>();
        List<String> action_movies = new ArrayList<String>();
        List<String> romantic_movies = new ArrayList<String>();
        List<String> horror_movies = new ArrayList<String>();
        List<String> comedy_movies = new ArrayList<String>();
        action_movies.add("failure to land");
        action_movies.add("Abcdeef");
        action_movies.add("Lmn");
        romantic_movies.add("KLM");
        romantic_movies.add("OOOP");
        romantic_movies.add("OOOP");
        horror_movies.add("CCCC");
        horror_movies.add("DDD");
        horror_movies.add("EEE");
        comedy_movies.add("TTT");
        comedy_movies.add("UUU");
        comedy_movies.add("EEE");
        versionDetails.put("Action_Movies",action_movies);
        versionDetails.put("Romantic_Movies",romantic_movies);
        versionDetails.put("Horror_Movies",horror_movies);
        versionDetails.put("Comedy_Movies",comedy_movies);
        return versionDetails;
    }
}
