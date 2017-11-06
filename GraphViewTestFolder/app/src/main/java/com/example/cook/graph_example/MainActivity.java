package com.example.cook.graph_example;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jonas on 10.09.16.
 */
public class MainActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> mSeries;

    private final Handler mHandler = new Handler();
    private Runnable mTimer2;
    int size =0;
    double counter =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GraphView graph = findViewById(R.id.graph);
        initGraph(graph);
    }

    public void initGraph(GraphView graph) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        Query query = ref.child("SongByDay");
        mSeries = new LineGraphSeries<>();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for(DataSnapshot SONGID: dataSnapshot.getChildren()) {
                        for (DataSnapshot GENRE : SONGID.getChildren()) {
                            long count = GENRE.getChildrenCount();
                            Log.e("COUNTER",count + "");
                            int count1 = (int)count + 0;
                            size = count1;
                            Log.e("COUNTER",count1 + "");

                            for (DataSnapshot DATE : GENRE.getChildren()) {
                                String datesting = DATE.getKey();
                                DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                                try {
                                    Date theday = df.parse(datesting);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                for(DataSnapshot POINT: DATE.getChildren()) {
                                    Log.e("COUNT", POINT.getValue() + "");
                                    long d = (long)POINT.getValue();

                                    mSeries.appendData(new DataPoint(counter++, d), true, count1 );
                                }

                            }
                            break;
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        double x = 0;
        // first mSeries is a line

        graph.addSeries(mSeries);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(12);

        graph.getViewport().setMaxY(105);
        graph.getViewport().setMinY(0);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);

        //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));


    }
}
