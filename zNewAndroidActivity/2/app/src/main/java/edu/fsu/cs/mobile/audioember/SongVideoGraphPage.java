package edu.fsu.cs.mobile.audioember;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by woody on 7/30/17.
 */

public class SongVideoGraphPage extends Fragment{
    // For the video
    //WebChromeClient mWebChromeClient = null;
    WebChromeClient mWebChromeClient = null;
    WebChromeClient chartChromeClient = null;
    View mCustomView;
    RelativeLayout mContentView;
    FrameLayout mCustomViewContainer;
    WebChromeClient.CustomViewCallback mCustomViewCallback;

    WebView myWebView;
    String html;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        String[] songHtml = {};
        String artistName;
        String songName;
        super.onCreate(savedInstanceState);
        getActivity().setContentView(R.layout.song_result);



        Bundle GraphBundle = getActivity().getIntent().getExtras();
        if (GraphBundle!=null)
        {
            songHtml= GraphBundle.getStringArray("embeddedGraphs");   //Getting the graphs from the intent to graph onto the page

            artistName = GraphBundle.getString("artist");
            songName = GraphBundle.getString("song");

            try {
                Document jSoupDocument = Jsoup.connect("https://www.youtube.com/results?search_query=" + songName + " by " + artistName).data("query", "Java").userAgent("Mozilla").cookie("auth", "token").timeout(3000).post();
                Element link = jSoupDocument.select("yt-uix-tile-link").first();
                String AttrEmbed = link.attr("href");
                String url = "https://www.youtube.com/embed/" + AttrEmbed.substring(AttrEmbed.indexOf(9)) +"?autoplay=1";
                Log.d("UrlBeingParsed", url);

            }
            catch (IOException e) {
                e.printStackTrace();
            }

            //
            WebView chart = (WebView) getActivity().findViewById(R.id.chart_webview);
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
            html = "<iframe id=\"igraph\" scrolling=\"no\" style=\"border:none;\" seamless=\"seamless\" src=\"https://plot.ly/~audioembers/21985.embed\" height=\"525\" width=\"100%\"></iframe>";
            chart.loadData(html, "text/html", null);

            // For Video
            myWebView = (WebView) getActivity().findViewById(R.id.video_webview);
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
            myWebView.loadUrl("https://www.youtube.com/watch?v=YQHsXMglC9A");
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.song_result_menu, menu);
        return true;
    }*/


    public class MyWebChromeClient extends WebChromeClient {

        FrameLayout.LayoutParams LayoutParameters = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomViewContainer = new FrameLayout(getContext());
            mCustomViewContainer.setLayoutParams(LayoutParameters);
            mCustomViewContainer.setBackgroundResource(android.R.color.black);
            view.setLayoutParams(LayoutParameters);
            mCustomViewContainer.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);
            getActivity().setContentView(mCustomViewContainer);
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
                getActivity().setContentView(mContentView);
            }
        }
    }

    public void onBackPressed() {
        if (mCustomViewContainer != null)
            mWebChromeClient.onHideCustomView();
        else if (myWebView.canGoBack())
            myWebView.goBack();
        else
            getActivity().onBackPressed();
    }
}
