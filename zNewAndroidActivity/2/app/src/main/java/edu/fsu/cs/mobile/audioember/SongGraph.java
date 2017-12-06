package edu.fsu.cs.mobile.audioember;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * Created by jonas on 10.09.16.
 */
public class SongGraph extends AppCompatActivity {

    private LineGraphSeries<DataPoint> mSeries;
    private final Handler mHandler = new Handler();
    private Runnable mTimer2;
    int size = 0;
    String FirstDate = "1958-01-01";
    Date theday1 = Calendar.getInstance().getTime();
    Date datelast = Calendar.getInstance().getTime();
    boolean firstdate=true;
    int counter =0;
    Vector<Date> datearray = new Vector<>();
    Vector<Long> pointarray = new Vector<>();
    boolean threadsleeper=false;
    Date datefirst;
    String SONGID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_graph);
        GraphView graph = findViewById(R.id.graph);
        SONGID = getIntent().getStringExtra("SONGID");
        graph.setBackgroundColor(Color.rgb(0,0,0));
        initGraph(graph);
    }

    public void initGraph(final GraphView graph) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        Log.e("FIREBASE", ref+"");
        final Query query = ref.child("SongByDay1").child(SONGID);

        Log.e("FIREBASE", query+"");
        mSeries = new LineGraphSeries<DataPoint>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        try{
            theday1 = df1.parse(FirstDate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        datefirst = theday1;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.e("FINALLY", "INHERE");
                    int genreCounter=0;
                    Date MinDate = Calendar.getInstance().getTime();
                    Date MaxDate = new Date(1,1,1);
                    for (DataSnapshot GENRE : dataSnapshot.getChildren()) {
                        long count = GENRE.getChildrenCount();
                        //Log.e("COUNTER", count + "");

                        int count1 = (int) count + 0;
                        size = count1;
                        //Log.e("COUNTER", count1 + "");

                        for (DataSnapshot DATE : GENRE.getChildren()) {
                            String datesting = DATE.getKey();
                            Date theday = Calendar.getInstance().getTime();
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                theday = df.parse(datesting);
                                datearray.add(theday);
                            }
                            catch (ParseException e) {
                                e.printStackTrace();
                            }
                            for (DataSnapshot POINT : DATE.getChildren()) {
                                //Log.e("COUNT", theday + "");
                                pointarray.add((Long) POINT.getValue());
                                counter++;
                            }
                        }
                        for(int i =0; i<pointarray.size(); i++)
                        {
                            final int i1 = i;

                            if(i==0) {
                                if(MinDate.compareTo(datearray.elementAt(i1))>0)
                                {
                                    MinDate = datearray.elementAt(i1);
                                }
                            }
                            if(i==pointarray.size()-1)
                            {
                                if (MaxDate.compareTo(datearray.elementAt(i1))<0){
                                    MaxDate = datearray.elementAt(i1);

                                }
                            }

                            mSeries.appendData(new DataPoint(datearray.elementAt(i),pointarray.elementAt(i1)), false, 250);

                            Log.e("VECTOR", pointarray.elementAt(i) + " " + datearray.elementAt(i));
                        }
                        String GenreString = GENRE.getKey();
                        switch (GenreString){
                            case "country-songs":
                                GenreString = "Country";
                                mSeries.setBackgroundColor(Color.argb(10,253,95,0));
                                mSeries.setColor(Color.rgb(253, 95, 0));
                                break;
                            case "dance-club-play-songs":
                                GenreString = "EDM";
                                mSeries.setColor(Color.rgb(51, 181, 229));
                                mSeries.setBackgroundColor(Color.argb(10,51,181,229));
                                break;
                            case "hot-100":
                                GenreString = "Hot-100";
                                mSeries.setColor(Color.rgb(204, 0, 0));
                                mSeries.setBackgroundColor(Color.argb(10,204,0,0));
                                break;
                            case "pop-songs":
                                GenreString = "POP";
                                mSeries.setColor(Color.rgb(255, 255, 0));
                                mSeries.setBackgroundColor(Color.argb(10,255,255,0));
                                break;
                            case "r-b-hip-hop-songs":
                                GenreString = "R&B Hip Hop";
                                mSeries.setColor(Color.rgb(255, 255, 255));
                                mSeries.setBackgroundColor(Color.argb(10,255,255,255));
                                break;
                            case "rock-songs":
                                GenreString = "Rock";
                                mSeries.setColor(Color.rgb(57, 255, 20));
                                mSeries.setBackgroundColor(Color.argb(10,57,0,0));
                        }
                        mSeries.setAnimated(true);
                        mSeries.setDrawDataPoints(true);
                        mSeries.setDrawBackground(true);
                        //mSeries.setDrawAsPath(true);
                        mSeries.setThickness(10);
                        mSeries.setTitle(GenreString);
                        graph.addSeries(mSeries);

                        graph.getViewport().setMaxX(MaxDate.getTime());
                        graph.getViewport().setMinX(MinDate.getTime());
                        graph.getViewport().setXAxisBoundsManual(true);

                        mSeries = new LineGraphSeries<DataPoint>();
                        datearray = new Vector<Date>();
                        pointarray = new Vector<>();
                        genreCounter++;
                        Log.e("Genre Counter",genreCounter+"");
                    }

                }
                else{
                    Log.e("FUCK", "FUCK");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        double x = 0;
        graph.getViewport().setMaxY(105);
        graph.getViewport().setMinY(0);
        // graph.getGridLabelRenderer().setHumanRounding(false);
        //graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setScrollable(true);
        graph.getGridLabelRenderer().setGridColor(Color.rgb(180,23,212));
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.rgb(180,23,212));
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.rgb(180,23,212));
        graph.setTitle("SongTitle");
        graph.setTitleColor(Color.red(204));
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setWidth(400);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(20);
        graph.getLegendRenderer().setTextColor(Color.WHITE);
        //graph.getGridLabelRenderer().setVerticalAxisTitle("P  o  i  n  t  s");
        //graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.rgb(204,0,0));
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        Log.e("SIZE", pointarray.size() + "");
    }
}