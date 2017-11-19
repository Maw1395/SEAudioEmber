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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button selection1 = findViewById(R.id.song);
        selection1.setOnClickListener(this);

        Button selection2 = findViewById(R.id.artist);
        selection2.setOnClickListener(this);

        Button selection3 = findViewById(R.id.year);
        selection3.setOnClickListener(this);

        Button selection4 = findViewById(R.id.genre);
        selection4.setOnClickListener(this);
    }
        @Override
        public void onClick (View v){
            switch(v.getId())
            {
                case R.id.song:
                {
                    //startActivity(new Intent(MainActivity.this, SongListView.class));
                    break;
                }

                case R.id.artist:
                {
                   // startActivity(new Intent(MainActivity.this, ArtistView.class));
                    break;
                }

                case R.id.year:
                {
                   // startActivity(new Intent(MainActivity.this, YearView.class));
                    break;
                }

                case R.id.genre:
                {
                    //startActivity(new Intent(MainActivity.this, GenreListview.class));
                    break;
                }
            }
        }

}
