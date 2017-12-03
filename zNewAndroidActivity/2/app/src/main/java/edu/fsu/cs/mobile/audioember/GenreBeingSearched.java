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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Woodham-PC on 12/2/2017.
 */

public class GenreBeingSearched extends AppCompatActivity {

    private ArrayList<String> songs = new ArrayList<String>();

    private String Genre;
    private String Year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  songs = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genre_being_searched);
        ListView list  = (ListView) findViewById(R.id.genreList);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, songs);
        final int counterIntent = 50;
        final int counter = counterIntent;
        Year = "2010";
        Genre = getIntent().getStringExtra("Genre");
        Genre = "SongPointsByYear" + Genre;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        int i = 0;

        for (i = 1; i < 51; i++) {
            Query query = ref.child(Genre).child(Year).child((i + counter) + "");
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
                                case "SongID":
                                    break;
                                case "SongName":
                                    SongName = Rank.getValue() + "";
                                    break;
                            }


                        }
                        songs.add(" " + SongName + " by " + Artist + " " + Points);
                        Log.e("Songs", " " + SongName + " by " + Artist + " " + Points);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Log.e("i value", i + "");
            if (i == 50) {
                for (int j =0; j<songs.size(); j++)
                    Log.e("SONG IN LIST",songs.get(j));
                setup(list, adapter);
            }
        }
    }
    public ListView setup(ListView list, ArrayAdapter<String> adapter)
    {
        adapter = new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, songs);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
       /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long id) {
                Toast.makeText(this, "" + pos, Toast.LENGTH_LONG);

            }
        }); */





       /* Button Rock = findViewById(R.id.Rock);
        Rock.setOnClickListener(this);*/
       return list;
    }
   /* @Override
    public void onClick(View v) {
        Intent i = new Intent(getBaseContext(), GenreBeingSearched.class);
        switch (v.getId()) {
            case R.id.Rock: {
                i.putExtra("Genre", "-pop-songs");
                startActivity(i);
                break;
            }
        }
    }*/
}

