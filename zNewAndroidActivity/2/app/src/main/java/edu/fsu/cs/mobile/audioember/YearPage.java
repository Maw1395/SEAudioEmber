package edu.fsu.cs.mobile.audioember;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by woody on 12/4/17.
 */

public class YearPage extends AppCompatActivity {
    final private ArrayList<String> years = new ArrayList<String>();
    final private ArrayList<String> SongID = new ArrayList<String>();

    private String Genre;
    private String Year;
    private boolean songOrArtist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  songs = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yearpage);
        ListView list = (ListView) findViewById(R.id.genreList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, years);
        Genre = getIntent().getStringExtra("GENRE");
        String GenreString = Genre;
        switch (GenreString){
            case "-country-songs":
                GenreString = "Country";
                break;
            case "-dance-club-play-songs":
                GenreString = "EDM";
                break;
            case "-hot-100":
                GenreString = "Hot";
                break;
            case "-pop-songs":
                GenreString = "POP";
                break;
            case "-r-b-hip-hop-songs":
                GenreString = "R & B";
                break;
            case "-rock-songs":
                GenreString = "Rock";
        }
        songOrArtist = getIntent().getBooleanExtra("SONGNOTARTIST", true);
        if(songOrArtist)
        {
            this.setTitle("Top Songs > " +GenreString + " > Select Year");
        }
        else
        {
            this.setTitle("Top Artists > " +GenreString + " > Select Year");
        }
        switch(Genre)
        {
            case "-country-songs":
            {
                for(int i =1958; i<2018; i++)
                    years.add(i+"");
                adapter = new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, years);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            }
            case "-r-b-hip-hop-songs":
            {
                for(int i =1958; i<2018; i++)
                    years.add(i+"");
                adapter = new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, years);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            }
            case "-dance-club-play-songs":
            {
                for(int i =1976; i<2018; i++)
                    years.add(i+"");
                adapter = new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, years);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            }
            case "-pop-songs":
            {
                for(int i =1992; i<2018; i++)
                    years.add(i+"");
                adapter = new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, years);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            }
            case "-hot-100":
            {
                for(int i =1958; i<2018; i++)
                    years.add(i+"");
                adapter = new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, years);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            }
            case "-rock-songs":
            {
                for(int i =2009; i<2018; i++)
                    years.add(i+"");
                adapter = new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, years);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            }
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View list, int pos, long id) {

                if (songOrArtist)

                    {
                        Intent i = new Intent(getBaseContext(), SongPage.class);
                        i.putExtra("GENRE", Genre);
                        i.putExtra("YEAR_RANGE", adapter.getItemAtPosition(pos)+"");
                        Log.e( "I'm BEING CLICKED", adapter.getItemAtPosition(pos)+"");
                        startActivity(i);
                    }
                else{
                    Intent i = new Intent(getBaseContext(), ArtistPage.class);
                    i.putExtra("GENRE", Genre);
                    i.putExtra("YEAR_RANGE", adapter.getItemAtPosition(pos)+"");
                    Log.e( "I'm BEING CLICKED", adapter.getItemAtPosition(pos)+"");
                    startActivity(i);
                }


            }
        });
    }
}
