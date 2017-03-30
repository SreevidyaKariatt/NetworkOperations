package robosoft.com.networkassignment.manager;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import java.net.URL;
import java.net.URLConnection;

import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.io.IOException;
import java.io.InputStream;
import robosoft.com.networkassignment.dto.Listwrapper;
import robosoft.com.networkassignment.network.GsonRequest;
import robosoft.com.networkassignment.network.VolleyHelper;

/**
 * Created by sree on 13/12/15.
 */
public class DownloadManager {


    private static String myTag = "StackSites";
    static final int POST_PROGRESS = 1;

    public static void downloadData(Context context,String url,Response.Listener<Listwrapper> listener,Response.ErrorListener errorListener)
    {
        GsonRequest<Listwrapper> request = new GsonRequest<Listwrapper>(url, Listwrapper.class, listener, errorListener);
        request.setTag(url);
        VolleyHelper.getInstance(context).addToRequestQueue(request);
    }

    public static void DownloadFromUrl(String URL, FileOutputStream fos) {  //this is the downloader method
        try {

            URL url = new URL(URL);

            long startTime = System.currentTimeMillis();



            URLConnection ucon = url.openConnection();

            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte data[] = new byte[1024];

            int count;

            while ((count = bis.read(data)) != -1) {

                bos.write(data, 0, count);
            }

            bos.flush();
            bos.close();


        } catch (IOException e) {

        }
    }
}
