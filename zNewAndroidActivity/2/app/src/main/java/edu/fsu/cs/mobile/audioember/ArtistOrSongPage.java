package edu.fsu.cs.mobile.audioember;

/**
 * Created by woody on 12/4/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ArtistOrSongPage extends AppCompatActivity implements View.OnClickListener {
    String Genre;
    String YearRange;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        Genre = getIntent().getStringExtra("GENRE");
        YearRange = getIntent().getStringExtra("YEAR_RANGE");
        String GenreString = Genre;
        switch (GenreString){
            case "-country-songs":
                GenreString = "Country";
                break;
            case "-dance-club-play-songs":
                GenreString = "EDM";
                break;
            case "-hot-100":
                GenreString = "Hot";
                break;
            case "-pop-songs":
                GenreString = "POP";
                break;
            case "-r-b-hip-hop-songs":
                GenreString = "R & B";
                break;
            case "-rock-songs":
                GenreString = "Rock";
                break;
            case "-all-time":
                GenreString="All Time";
        }
        this.setTitle("Genre > " + GenreString + " > " + YearRange);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpage);

        Button searchAllTime = findViewById(R.id.AllTime);
        searchAllTime.setText("Artist");
        searchAllTime.setOnClickListener(this);

       /* Button searchGenre = findViewById(R.id.Genre);
        searchGenre.setText("Song");
        searchGenre.setOnClickListener(this);*/

    }

    @Override
    public void onClick (View v)
    {
        switch(v.getId())
        {

            case R.id.AllTime:
            {
                Intent i = new Intent(getBaseContext(), ArtistPage.class);
                i.putExtra("GENRE", Genre);
                i.putExtra("YEAR_RANGE", YearRange);
                startActivity(i);
                break;
            }

            /*case R.id.Genre:
            {
                Intent i = new Intent(getBaseContext(),SongPage.class);
                i.putExtra("GENRE", Genre);
                i.putExtra("YEAR_RANGE", YearRange);
                startActivity(i);
                break;
            }*/

        }
    }
}
