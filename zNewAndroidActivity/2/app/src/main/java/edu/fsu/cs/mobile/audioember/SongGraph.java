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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GraphView graph = findViewById(R.id.graph);
        graph.setBackgroundColor(Color.rgb(0,0,0));
        initGraph(graph);
    }

    public void initGraph(final GraphView graph) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        Log.e("FIREBASE", ref+"");
        final Query query = ref.child("SongByDay1").child("1");

        Log.e("FIREBASE", query+"");
        mSeries = new LineGraphSeries<>();
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
                    for (DataSnapshot GENRE : dataSnapshot.getChildren()) {
                        long count = GENRE.getChildrenCount();
                        Log.e("COUNTER", count + "");
                        int count1 = (int) count + 0;
                        size = count1;
                        Log.e("COUNTER", count1 + "");

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
                                Log.e("COUNT", theday + "");
                                pointarray.add((Long) POINT.getValue());
                                counter++;
                            }
                        }
                        break;
                    }
                    for(int i =0; i<pointarray.size(); i++)
                    {
                        final int i1 = i;
                        mSeries.appendData(new DataPoint(datearray.elementAt(i1),pointarray.elementAt(i1)), false, size);
                        graph.addSeries(mSeries);
                        Log.e("VECTOR", pointarray.elementAt(i) + " " + datearray.elementAt(i));
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
        //graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setScrollable(true);
        graph.getGridLabelRenderer().setGridColor(Color.rgb(180,23,212));
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.rgb(180,23,212));
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.rgb(180,23,212));
        graph.setTitle("SongTitle");
        graph.setTitleColor(Color.red(204));
        //graph.getGridLabelRenderer().setVerticalAxisTitle("P  o  i  n  t  s");
        //graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.rgb(204,0,0));
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        Log.e("SIZE", pointarray.size() + "");
    }
}