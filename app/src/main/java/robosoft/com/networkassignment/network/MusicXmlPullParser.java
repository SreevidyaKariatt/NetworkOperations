package robosoft.com.networkassignment.network;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


import robosoft.com.networkassignment.dto.Music;

/**
 * Created by sree on 15/12/15.
 */
public class MusicXmlPullParser {

    static final String SONG="song";
    static final String ID = "id";
    static final String TITLE = "title";
    static final String ARTIST = "artist";
    static final String DURATION = "duration";
    static final String THUMB_URL = "thumb_url";

    public static List<Music> getStackSitesFromFile(Context ctx) {


        List<Music> musicSites;
        musicSites = new ArrayList<Music>();
         Music curMusicSite = null;
        String curText = "";

        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            FileInputStream fis = ctx.openFileInput("MusicAlbum.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            xpp.setInput(reader);
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagname = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(SONG)) {
                            curMusicSite = new Music();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        curText = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase(SONG)) {
                            musicSites.add(curMusicSite);
                        } else if (tagname.equalsIgnoreCase(ID)) {
                            curMusicSite.setId(curText);
                        } else if (tagname.equalsIgnoreCase(TITLE)) {
                            curMusicSite.setTitle(curText);
                        } else if (tagname.equalsIgnoreCase(ARTIST)) {
                            curMusicSite.setArtist(curText);
                        }else if (tagname.equalsIgnoreCase(DURATION)) {
                            curMusicSite.setDuration(curText);
                        }
                        else if (tagname.equalsIgnoreCase(THUMB_URL)) {
                            curMusicSite.setThumb_url(curText);
                        }
                        break;

                    default:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return musicSites;
    }
}
