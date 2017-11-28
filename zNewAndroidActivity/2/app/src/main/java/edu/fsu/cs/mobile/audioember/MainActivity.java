package edu.fsu.cs.mobile.audioember;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                startActivity(new Intent(MainActivity.this, SearchBySong.class));
                break;
            }

            case R.id.artist:
            {
                 startActivity(new Intent(MainActivity.this, Search_by_Artist.class));
                break;
            }

            case R.id.year:
            {
                // startActivity(new Intent(MainActivity.this, YearView.class));
                break;
            }

            case R.id.genre:
            {
                startActivity(new Intent(MainActivity.this, Search_by_Genre.class));
                break;
            }

            case R.id.AllTime:
            {
                //startActivity(new Intent(MainActivity.this, All_Time_List));
                break;
            }
        }
    }

}
