package edu.fsu.cs.mobile.audioember;

/**
 * Created by woody on 12/4/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GenrePage extends AppCompatActivity implements View.OnClickListener{
    public boolean songOrArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genrepage);

        Button Rock = findViewById(R.id.Rock);
        Rock.setOnClickListener(this);

        Button Pop = findViewById(R.id.Pop);
        Pop.setOnClickListener(this);

        Button Top100 = findViewById(R.id.Top100);
        Top100.setOnClickListener(this);

        Button Hip_Hop = findViewById(R.id.Hip_Hop);
        Hip_Hop.setOnClickListener(this);

        Button DCP = findViewById(R.id.DCP);
        DCP.setOnClickListener(this);

        Button Country = findViewById(R.id.Country);
        Country.setOnClickListener(this);

        Button Back = findViewById(R.id.Back);
        Back.setOnClickListener(this);
        songOrArtist = getIntent().getBooleanExtra("SONGNOTARTIST", true);
        if(songOrArtist)
        {
            this.setTitle("Top Songs > Select Genre");
        }
        else
        {
            this.setTitle("Top Artists > Select Genre");
        }
    }

    @Override
    public void onClick (View v)
    {
        Intent i = new Intent(getBaseContext(), YearPage.class);
        i.putExtra("SONGNOTARTIST", songOrArtist);
        switch(v.getId())
        {
            case R.id.Pop:
            {
                i.putExtra("GENRE", "-pop-songs");
                startActivity(i);
                break;
            }

            case R.id.Hip_Hop:
            {
                i.putExtra("GENRE", "-r-b-hip-hop-songs");
                startActivity(i);
                break;
            }

            case R.id.Rock:
            {
                i.putExtra("GENRE", "-rock-songs");
                startActivity(i);
                break;
            }

            case R.id.Top100:
            {
                i.putExtra("GENRE", "-hot-100");
                startActivity(i);
                break;
            }

            case R.id.DCP:
            {
                i.putExtra("GENRE", "-dance-club-play-songs");
                startActivity(i);
                break;
            }

            case R.id.Country:
            {
                i.putExtra("GENRE", "-country-songs");
                startActivity(i);
                break;
            }

            case R.id.Back:
            {
                startActivity(new Intent(GenrePage.this, FrontPage.class));
                break;
            }
        }
    }
}

