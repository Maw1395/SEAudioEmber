package edu.fsu.cs.mobile.audioember;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by woody on 12/4/17.
 */

public class ArtistPage extends AppCompatActivity implements View.OnClickListener {

    final private ArrayList<String> songs = new ArrayList<String>();
    final private ArrayList<String> SongID = new ArrayList<String>();

    private String Genre;
    private String Genre1;
    private String Year;
    private int Page;
    private Boolean End;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  songs = new ArrayList<String>();
        intent = new Intent(getBaseContext(), SongGraph.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genre_being_searched);
        final Button NextButton = (Button) findViewById(R.id.Next);
        NextButton.setOnClickListener(this);
        final ListView list = (ListView) findViewById(R.id.genreList);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, songs);
        Page = getIntent().getIntExtra("PAGE", 0);
        //Log.e("PAGENUMBER", Page+"");
        int counterIntent = getIntent().getIntExtra("PAGE", 0);
        counterIntent *= 50;
        final int counter = counterIntent;
        Log.e("PAGENUMBER", counterIntent + "");
        Year = getIntent().getStringExtra("YEAR_RANGE");

        Genre = getIntent().getStringExtra("GENRE");
        Genre1 = Genre;
        Genre = "ArtistPointsByYear" + Genre;
        Log.e("GENRE", Genre);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        int i = 0;

        for (i = 1; i < 51; i++) {
            Query query = ref.child(Genre).child(Year).child((counter + i) + "");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String Artist = "";
                        String Points = "";
                        String SongName = "";
                        for (DataSnapshot Rank : dataSnapshot.getChildren()) {
                            String key = Rank.getKey();
                            switch (key) {
                                case "Artist":
                                    Artist = Rank.getValue() + "";
                                    break;
                                case "Points":
                                    Points = Rank.getValue() + "";
                                    break;
                            }


                        }
                        songs.add(Artist + " " + Points);
                        //Log.e("Songs", " " + SongName + " by " + Artist + " " + Points);
                        setup(list, adapter);
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Log.e("Nothing", "NOTHING");
                }
            });
            //Log.e("i value", i + "");
            if (i == 50) {
                for (int j = 0; j < songs.size(); j++)
                    //Log.e("SONG IN LIST",songs.get(j));
                    setup(list, adapter);
            }
        }

    }

    public ListView setup(ListView list, ArrayAdapter<String> adapter) {
        adapter = new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, songs);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View list, int pos, long id) {

                /*intent.putExtra("SONGID", SongID.get(pos));
                startActivity(intent);*/
                //Log.e("I'm BEING CLICKED", SongID.get(pos));

            }
        });


       /* Button Rock = findViewById(R.id.Rock);
        Rock.setOnClickListener(this);*/
        return list;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getBaseContext(), ArtistPage.class);
        switch (v.getId()) {
            case R.id.Next: {
                i.putExtra("GENRE", Genre1);
                i.putExtra("PAGE", Page + 1);
                i.putExtra("YEAR_RANGE", Year);
                startActivity(i);
                break;
            }

        }
    }
}
