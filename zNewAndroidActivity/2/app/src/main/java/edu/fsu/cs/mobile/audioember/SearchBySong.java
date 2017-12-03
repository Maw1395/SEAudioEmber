package edu.fsu.cs.mobile.audioember;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Cameron Porter on 11/19/2017.
 */

public class SearchBySong extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_by_song);

        Button SearchSong = findViewById(R.id.Search);
        SearchSong.setOnClickListener(this);

        Button Back = findViewById(R.id.Back);
        Back.setOnClickListener(this);
    }

    @Override
    public void onClick (View v)
    {
        switch(v.getId())
        {
            case R.id.Search:
            {
                //startActivity(new Intent(SearchBySong.this, SongResults.class));
                break;
            }

            case R.id.Back:
            {
                startActivity(new Intent(SearchBySong.this, SearchMenu.class));
                break;
            }
        }
    }
}