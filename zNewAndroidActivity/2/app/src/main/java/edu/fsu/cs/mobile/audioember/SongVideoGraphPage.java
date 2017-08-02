package edu.fsu.cs.mobile.audioember;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

import static edu.fsu.cs.mobile.audioember.R.id.container;

/**
 * Created by woody on 7/30/17.
 */

public class SongVideoGraphPage extends Fragment{

    WebChromeClient mWebChromeClient = null;
    WebChromeClient chartChromeClient = null;
    View mCustomView;
    RelativeLayout mContentView;
    FrameLayout mCustomViewContainer;
    WebChromeClient.CustomViewCallback mCustomViewCallback;

    WebView YouTubeVideo;
    WebView chart0;
    WebView chart1;
    WebView chart2;
    WebView chart3;
    WebView chart4;
    WebView chart5;
    WebView[] webViewArray = new WebView[7];
    WebChromeClient[] webChromeArray = new WebChromeClient[7];
    String html;
    String[] SongHtml = {};


    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        ScrollView sv = new ScrollView(getContext());
        sv.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final LinearLayout layout = new LinearLayout(getContext());
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getActivity().getWindowManager().getDefaultDisplay().
                getWidth(),getActivity().getWindowManager().getDefaultDisplay().getHeight());

        layout.setLayoutParams(params);
        String color = "2b2828";
        sv.setBackgroundColor(Integer.parseInt(color,16));
        layout.setBackgroundColor(Integer.parseInt(color,16));
        layout.setDrawingCacheBackgroundColor(Integer.parseInt(color,16));
        sv.setDrawingCacheBackgroundColor(Integer.parseInt(color,16));
        layout.setOrientation(LinearLayout.VERTICAL);
        sv.addView(layout);
        final LinearLayout.LayoutParams webViewParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Bundle b = getArguments();
        String songName;
        String artistName;
        ArrayList<String> GraphUrl = new ArrayList<String>();
        if(b!=null)
        {
            songName = b.getString("Title");
            artistName= b.getString("Artist");
            GraphUrl = b.getStringArrayList("GraphUrls");
        }
        else
        {
            songName = "Closer";
            artistName= "ChainSmokers Featuring Halsey";
            GraphUrl.add("<iframe id=\"igraph\" scrolling=\"no\" style=\"border:none;\" seamless=\"seamless\" src=\"https://plot.ly/~audioembers/20335.embed\" height=\"525\" width=\"100%\"></iframe>");
            GraphUrl.add("<iframe id=\"igraph\" scrolling=\"no\" style=\"border:none;\" seamless=\"seamless\" src=\"https://plot.ly/~audioembers/20337.embed\" height=\"525\" width=\"100%\"></iframe>");
            GraphUrl.add("<iframe id=\"igraph\" scrolling=\"no\" style=\"border:none;\" seamless=\"seamless\" src=\"https://plot.ly/~audioembers/20339.embed\" height=\"525\" width=\"100%\"></iframe>");


        }
        final ArrayList<String> GraphUrls= GraphUrl;

        final String youtubeSearchQuery = "https://www.youtube.com/results?search_query=" + songName + " by " + artistName;
        Thread ConnectionThread = new Thread() {
            public void run()
            {
                Document jSoupDocument = null;
                try {
                    jSoupDocument = Jsoup.connect(youtubeSearchQuery).get();
                    final String youtubeEmbedLink=jSoupDocument.select("a.yt-uix-tile-link").first().attr("abs:href");



                    final String youtubeUrl="<iframe width=\"100%\" height=\"355\" src=\"https://www.youtube.com/embed/"+ youtubeEmbedLink.substring(youtubeEmbedLink.indexOf("?v=") +3) +"\" frameborder=\"0\" allowfullscreen==\"allowfullscreen\"></iframe>";

                    if(youtubeEmbedLink!=null)
                    {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                webViewArray[0].loadData(youtubeUrl, "text/html", "UTF-8");
                                layout.addView(webViewArray[0], webViewParams);
                                for (int i =0; i< GraphUrls.size(); i++)
                                {
                                    webViewArray[i+1].loadData(GraphUrls.get(i), "text/html",null);
                                    layout.addView(webViewArray[i+1], webViewParams);

                                }
                            }
                        });

                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        };


        for (int i=0; i<webViewArray.length; i++)
        {
            webViewArray[i] = new WebView(getContext());
            webChromeArray[i] = new WebChromeClient();
            webViewArray[i].setWebChromeClient(webChromeArray[i]);
            webViewArray[i].setWebViewClient(new WebViewClient()
            {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url)
                {
                    return false;
                }
            });
            WebSettings webset = webViewArray[i].getSettings();
            webset.setJavaScriptEnabled(true);
        }

        ConnectionThread.start();

        //LinearLayout l = (LinearLayout) rootView.findViewById(R.id.Scroller);
        //l.addView(sv);

        return sv;
    }

}
