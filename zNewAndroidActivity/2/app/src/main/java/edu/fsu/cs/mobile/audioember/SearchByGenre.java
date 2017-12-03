package edu.fsu.cs.mobile.audioember;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchByGenre extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_by_genre);

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

        Button AllTime = findViewById(R.id.AllTime);
        AllTime.setOnClickListener(this);
    }

    @Override
    public void onClick (View v)
    {
        Intent i = new Intent(getBaseContext(), GenreBeingSearched.class);
        switch(v.getId())
        {
            case R.id.Pop:
            {
                i.putExtra("Genre", "-pop-songs");
                startActivity(i);
                break;
            }

            case R.id.Hip_Hop:
            {
                i.putExtra("Genre", "-r-b-hip-hop-songs");
                startActivity(i);
                break;
            }

            case R.id.Rock:
            {
                i.putExtra("Genre", "-rock-songs");
                startActivity(i);
                break;
            }

            case R.id.Top100:
            {
                i.putExtra("Genre", "-hot-100");
                startActivity(i);
                break;
            }

            case R.id.DCP:
            {
                i.putExtra("Genre", "-dance-club-play-songs");
               startActivity(i);
                break;
            }

            case R.id.Country:
            {
                i.putExtra("Genre", "-country-songs");
               startActivity(i);
                break;
            }

            case R.id.Back:
            {
                startActivity(new Intent(SearchByGenre.this, SearchMenu.class));
                break;
            }
            case R.id.AllTime:
            {
                i.putExtra("Genre", "");
                startActivity(i);
                break;
            }
        }
    }
}
