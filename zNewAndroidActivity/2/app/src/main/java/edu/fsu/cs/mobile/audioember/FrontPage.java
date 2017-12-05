package edu.fsu.cs.mobile.audioember;

/**
 * Created by woody on 12/4/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FrontPage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpage);

        Button searchAllTime = findViewById(R.id.AllTime);
        searchAllTime.setOnClickListener(this);

        Button searchGenre = findViewById(R.id.Genre);
        searchGenre.setOnClickListener(this);

    }

    @Override
    public void onClick (View v)
    {
        switch(v.getId())
        {

            case R.id.AllTime:
            {
                Intent i = new Intent(getBaseContext(), ArtistOrSongPage.class);
                i.putExtra("GENRE", "-all-time");
                i.putExtra("YEAR_RANGE", "1960-2015");
                startActivity(i);
                break;
            }

            case R.id.Genre:
            {
                Intent i = new Intent(getBaseContext(), GenrePage.class);
                startActivity(i);
                break;
            }

        }
    }
}