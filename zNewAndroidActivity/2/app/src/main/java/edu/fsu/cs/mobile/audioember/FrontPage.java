package edu.fsu.cs.mobile.audioember;

/**
 * Created by woody on 12/4/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jjoe64.graphview.GraphView;

public class FrontPage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpage);

        Button searchAllTimeArtist = findViewById(R.id.AllTimeArtist);
        searchAllTimeArtist.setOnClickListener(this);

        Button searchAllTimeSong = findViewById(R.id.AllTimeSong);
        searchAllTimeSong.setOnClickListener(this);

        Button searchGenreArtist = findViewById(R.id.GenreArtist);
        searchGenreArtist.setOnClickListener(this);

        Button searchGenreSong = findViewById(R.id.GenreSong);
        searchGenreSong.setOnClickListener(this);

        GraphView graph = findViewById(R.id.graph);
        initGraph(graph);

    }
    public void initGraph(final GraphView graph) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        Log.e("FIREBASE", ref+"");
        final Query query = ref.child("SongByDay1").child(SONGID);
    @Override
    public void onClick (View v)
    {
        switch(v.getId())
        {

            case R.id.AllTimeArtist:
            {
                Intent i = new Intent(getBaseContext(), ArtistPage.class);
                i.putExtra("GENRE", "-all-time");
                i.putExtra("YEAR_RANGE", "1960-2015");
                startActivity(i);
                break;
            }
            case R.id.AllTimeSong:
            {
                Intent i = new Intent(getBaseContext(), SongPage.class);
                i.putExtra("GENRE", "-all-time");
                i.putExtra("YEAR_RANGE", "1960-2015");
                startActivity(i);
                break;
            }

            case R.id.GenreSong:
            {
                Intent i = new Intent(getBaseContext(), GenrePage.class);
                i.putExtra("SONGNOTARTIST", true);
                startActivity(i);
                break;
            }
            case R.id.GenreArtist:
            {
                Intent i = new Intent(getBaseContext(), GenrePage.class);
                i.putExtra("SONGNOTARTIST",false);
                startActivity(i);
                break;
            }

        }
    }
}