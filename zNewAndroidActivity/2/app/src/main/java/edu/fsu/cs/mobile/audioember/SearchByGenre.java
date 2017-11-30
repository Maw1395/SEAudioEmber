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
    }

    @Override
    public void onClick (View v)
    {
        switch(v.getId())
        {
            case R.id.Pop:
            {
                //startActivity(new Intent(Search_by_Genre.this, Pop.class));
                break;
            }

            case R.id.Hip_Hop:
            {
                //startActivity(new Intent(Search_by_Genre.this, Hip_Hop.class));
                break;
            }

            case R.id.Rock:
            {
                // startActivity(new Intent(Search_by_Genre.this, Rock.class));
                break;
            }

            case R.id.Top100:
            {
                //startActivity(new Intent(Search_by_Genre.this, Top100.class));
                break;
            }

            case R.id.DCP:
            {
                //startActivity(Search_by_Genre.this, DCP.class);
                break;
            }

            case R.id.Country:
            {
                //startActivity(Search_by_Genre.this, Country.class);
                break;
            }

            case R.id.Back:
            {
                startActivity(new Intent(SearchByGenre.this, SearchMenu.class));
                break;
            }
        }
    }
}
