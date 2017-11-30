package edu.fsu.cs.mobile.audioember;

/**
 * Created by Kevin Williams on 11/29/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SearchMenu extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_menu);

        Button searchSong = findViewById(R.id.song);
        searchSong.setOnClickListener(this);

        Button searchArtist = findViewById(R.id.artist);
        searchArtist.setOnClickListener(this);

        Button searchYear = findViewById(R.id.year);
        searchYear.setOnClickListener(this);

        Button searchGenre = findViewById(R.id.genre);
        searchGenre.setOnClickListener(this);

        Button AllTime = findViewById(R.id.AllTime);
        AllTime.setOnClickListener(this);
    }

    @Override
    public void onClick (View v)
    {
        switch(v.getId())
        {
            case R.id.song:
            {
                startActivity(new Intent(SearchMenu.this, SearchBySong.class));
                break;
            }

            case R.id.artist:
            {
                startActivity(new Intent(SearchMenu.this, SearchByArtist.class));
                break;
            }

            case R.id.year:
            {
                // startActivity(new Intent(SearchMenu.this, YearView.class));
                break;
            }

            case R.id.genre:
            {
                startActivity(new Intent(SearchMenu.this, SearchByGenre.class));
                break;
            }

            case R.id.AllTime:
            {
                //startActivity(new Intent(SearchMenu.this, All_Time_List));
                break;
            }
        }
    }

}