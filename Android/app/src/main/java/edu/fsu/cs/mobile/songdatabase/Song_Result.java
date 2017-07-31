package edu.fsu.cs.mobile.songdatabase;

/**
 * Created by Kevin Williams on 7/21/2017.
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Song_Result extends AppCompatActivity {
    // For the video
    //WebChromeClient mWebChromeClient = null;
    WebChromeClient mWebChromeClient = null;
    WebChromeClient chartChromeClient = null;
    View mCustomView;
    RelativeLayout mContentView;
    FrameLayout mCustomViewContainer;
    WebChromeClient.CustomViewCallback mCustomViewCallback;

    WebView myWebView;
    WebView chart;
    WebView[] webViewArray = new WebView[7];
    String html;
    String[] SongHtml = {};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.song_result);
        ScrollView sv = new ScrollView(this);
        sv.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        final LinearLayout layout = new LinearLayout(this);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getWindowManager().getDefaultDisplay().
                getWidth(),getWindowManager().getDefaultDisplay().getHeight());

        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        sv.addView(layout);
        setContentView(sv);
        final LinearLayout.LayoutParams webViewParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // Sets the text for the action bar title
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_song_result);

        // Chart webview
        //mWebView = (WebView) findViewById(R.id.video_webview);
        //mWebChromeClient = new WebChromeClient();

        // For chart
        final String songName = "SugarDaddy";
        final String artistName = "PajamaBand";
        final String url = "https://www.youtube.com/results?search_query=" + songName + " by " + artistName;
        Thread ConnectionThread = new Thread() {
        public void run()
            {
                Document jSoupDocument = null;
                try {
                    jSoupDocument = Jsoup.connect(url).get();
                    String youtubeEmbedLink=jSoupDocument.select("a.yt-uix-tile-link").first().attr("abs:href");



                   final String url="<iframe width=\"100%\" height=\"355\" src=\"https://www.youtube.com/embed/"+ youtubeEmbedLink.substring(youtubeEmbedLink.indexOf("?v=") +3) +"\" frameborder=\"0\" allowfullscreen==\"allowfullscreen\"></iframe>";
                   // Element link = jSoupDocument.select("a[href=/watch]").first();
                    //String AttrEmbed = link.attr("href");
                   //String url = "https://www.youtube.com/embed/" + testhref.substring(testhref.indexOf(".com/")) +"?autoplay=1";
                    Log.d("UrlBeingParsed", url);
                    if(youtubeEmbedLink!=null)
                    {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                myWebView.loadData(url, "text/html", "UTF-8");
                                webViewArray[0]=myWebView;
                                chart.loadData(html, "text/html", null);
                                webViewArray[1]=chart;
                                layout.addView(webViewArray[1], webViewParams);
                                layout.addView(webViewArray[0], webViewParams);
                                //chart.loadData(url,  "text/html", null);
                                //layout.addView(chart, webViewParams);
                            }
                        });

                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        };



        chart = new WebView(this);
        chartChromeClient = new WebChromeClient();
        chart.setWebChromeClient(chartChromeClient);
        chart.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return false;
            }
        });
        WebSettings chartSettings = chart.getSettings();
        chartSettings.setJavaScriptEnabled(true);
        html = "<iframe id=\"igraph\" scrolling=\"no\" style=\"border:none;\" seamless=\"seamless\" src=\"https://plot.ly/~audioembers/21985.embed\" height=\"355\" width=\"100%\"></iframe>";

        // For Video
        myWebView = new WebView(this);
        mWebChromeClient = new WebChromeClient();
        myWebView.setWebChromeClient(mWebChromeClient);
        myWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
       // myWebView.loadUrl("https://www.youtube.com/watch?v=YQHsXMglC9A");
        ConnectionThread.start();
    }
}