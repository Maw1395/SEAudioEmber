package edu.fsu.cs.mobile.audioember;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchByArtist extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_by_artist);

        Button searchArtist = findViewById(R.id.Search);
        searchArtist.setOnClickListener(this);

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
                //startActivity(new Intent(Search_by_Artist.this, Artist_Results.class));
                break;
            }

            case R.id.Back:
            {
                startActivity(new Intent(SearchByArtist.this, MainActivity.class));
                break;
            }
        }
    }
}

