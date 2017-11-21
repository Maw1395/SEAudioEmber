package edu.fsu.cs.mobile.audioember;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Cameron Porter 11/20/2017.
 */

public class Search_By_Song extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__by__song);

        Button Back = findViewById(R.id.Back);
        Back.setOnClickListener(this);

        Button Search = findViewById(R.id.Search);
        Search.setOnClickListener(this);
    }

    @Override
    public void onClick (View v){
        switch(v.getId())
        {
            case R.id.Back:
            {
                startActivity(new Intent(Search_By_Song.this, MainActivity.class));
                break;
            }

            case R.id.Search:
            {
                // startActivity(new Intent(MainActivity.this, ArtistView.class));
                break;
            }
        }
    }
}
