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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

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
    String html;
    String[] SongHtml = {};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_result);

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



                   final String url="<iframe width=\"100%\" height=\"140\" src=\"https://www.youtube.com/embed/"+ youtubeEmbedLink.substring(youtubeEmbedLink.indexOf("?v=") +3) +"\" frameborder=\"0\" allowfullscreen==\"allowfullscreen\"></iframe>";
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
                                chart.loadData(html, "text/html", null);
                            }
                        });

                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        };



        chart = (WebView) findViewById(R.id.chart_webview);
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
        html = "<iframe id=\"igraph\" scrolling=\"no\" style=\"border:none;\" seamless=\"seamless\" src=\"https://plot.ly/~audioembers/21985.embed\" height=\"250\" width=\"100%\"></iframe>";

        // For Video
        myWebView = (WebView) findViewById(R.id.video_webview);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.song_result_menu, menu);
        return true;
    }


    public class MyWebChromeClient extends WebChromeClient {

        FrameLayout.LayoutParams LayoutParameters = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mContentView = (RelativeLayout) findViewById(R.id.song_result_recyclerview);
            mContentView.setVisibility(View.GONE);
            mCustomViewContainer = new FrameLayout(Song_Result.this);
            mCustomViewContainer.setLayoutParams(LayoutParameters);
            mCustomViewContainer.setBackgroundResource(android.R.color.black);
            view.setLayoutParams(LayoutParameters);
            mCustomViewContainer.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);
            setContentView(mCustomViewContainer);
        }

        @Override
        public void onHideCustomView() {
            if (mCustomView == null) {
                return;
            } else {
                // Hide the custom view.
                mCustomView.setVisibility(View.GONE);
                // Remove the custom view from its container.
                mCustomViewContainer.removeView(mCustomView);
                mCustomView = null;
                mCustomViewContainer.setVisibility(View.GONE);
                mCustomViewCallback.onCustomViewHidden();
                // Show the content view.
                mContentView.setVisibility(View.VISIBLE);
                setContentView(mContentView);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mCustomViewContainer != null)
            mWebChromeClient.onHideCustomView();
        else if (myWebView.canGoBack())
            myWebView.goBack();
        else
            super.onBackPressed();
    }
}