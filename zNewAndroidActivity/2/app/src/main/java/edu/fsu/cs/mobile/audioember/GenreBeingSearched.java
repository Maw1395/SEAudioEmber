package edu.fsu.cs.mobile.audioember;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Woodham-PC on 12/2/2017.
 */

public class GenreBeingSearched extends AppCompatActivity {

    final private ArrayList<String> songs = new ArrayList<String>();
    final private ArrayList<String> SongID = new ArrayList<String>();

    private String Genre;
    private String Year;

    // 1 ----------- Pagination vars -------------------
    private RecyclerView recyclerView;
    //adapter object
    private RecyclerView.Adapter recyclerAdapter;

    //progress dialog
    private ProgressDialog progressDialog;

    // Load 50 songs per page
    private static final int TOTAL_ITEM_EACH_LOAD = 50;
    private int currentPage = 0;
    //------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 2 ------------------------------------------
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading 50 More Songs");
        progressDialog.show();
        //---------------------------------------------------

      //  songs = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genre_being_searched);
        final ListView list  = (ListView) findViewById(R.id.genreList);
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, songs);
        final int counterIntent = 50;
        final int counter = counterIntent;
        Year = "2010";
        Genre = getIntent().getStringExtra("Genre");
        Genre = "SongPointsByYear" + Genre;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        int i = 0;

        // 3 ----------------------------------------------
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(recyclerAdapter);

        for (i = 1; i < 51; i++)
        {
            Query query = ref.child(Genre).child(Year).child((counter+i)+"");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // 4 ----------------------------------------------
                    progressDialog.dismiss();
                    //-----------------------------------------------

                    if (dataSnapshot.exists()) {
                        String Artist = "";
                        String Points = "";
                        String SongName = "";
                        for (DataSnapshot Rank : dataSnapshot.getChildren()) {
                            String key = Rank.getKey();
                            switch (key) {
                                case "Artist":
                                    Artist = Rank.getValue() + "";
                                    break;
                                case "Points":
                                    Points = Rank.getValue() + "";
                                    break;
                                case "SongID":
                                    SongID.add(Rank.getValue() + "");
                                    break;
                                case "SongName":
                                    SongName = Rank.getValue() + "";
                                    break;
                            }


                        }
                        songs.add(  SongName + " by " + Artist + " " + Points);
                        Log.e("Songs", " " + SongName + " by " + Artist + " " + Points);
                        setup(list, adapter);

                        // 5 ----------------------------------------
                        recyclerView.setAdapter(recyclerAdapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
            Log.e("i value", i + "");
            if (i == 50) {
                for (int j =0; j<songs.size(); j++)
                    Log.e("SONG IN LIST",songs.get(j));
                    setup(list, adapter);
            }
        }
    }
    public ListView setup(ListView list, ArrayAdapter<String> adapter)
    {
        adapter = new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, songs);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View list, int pos, long id) {

               Log.e( "I'm BEING CLICKED", SongID.get(pos));

            }
        });





       /* Button Rock = findViewById(R.id.Rock);
        Rock.setOnClickListener(this);*/
       return list;
    }
   /* @Override
    public void onClick(View v) {
        Intent i = new Intent(getBaseContext(), GenreBeingSearched.class);
        switch (v.getId()) {
            case R.id.Rock: {
                i.putExtra("Genre", "-pop-songs");
                startActivity(i);
                break;
            }
        }
    }*/
}

