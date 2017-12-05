package edu.fsu.cs.mobile.audioember;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    // ----------------------------------------------------------------------------
    ListView lv;
    Button nextBtn, prevBtn;
    Pagination p = new Pagination();
    private int totalPages = Pagination.TOTAL_NUM_ITEMS / Pagination.ITEMS_PER_PAGE;
    private int currentPage = 0;
    //------------------------------------------------------------------------------

    final private ArrayList<String> songs = new ArrayList<String>();
    final private ArrayList<String> SongID = new ArrayList<String>();

    private String Genre;
    private String Year;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
      //  songs = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genre_being_searched);

        //-----------------------------INIATILZE VIEW------------------------------------------------------------------------
        //lv = (ListView) findViewById(R.id.lv);
        final ListView lv  = (ListView) findViewById(R.id.lv);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        prevBtn = (Button) findViewById(R.id.prevBtn);
        prevBtn.setEnabled(false);

        //SHOW TOAST WHEN LISTVIEW ITEM IS CLICKED
        //lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        //{
        //    @Override
        //    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        //    {
        //       Toast.makeText(GenreBeingSearched.this, p.generatePage(currentPage).get(i), Toast.LENGTH_SHORT).show();
        //    }
        //});

        //SET ADAPTER TO LISTVIEW
        lv.setAdapter(new ArrayAdapter<String>(GenreBeingSearched.this, android.R.layout.simple_list_item_1, p.generatePage(currentPage)));

        //NAVIGATE TO NEXT PAGE.
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage += 1;
                //enableDisableButtons();
                lv.setAdapter(new ArrayAdapter<String>(GenreBeingSearched.this, android.R.layout.simple_list_item_1, p.generatePage(currentPage)));
                toggleButtons();
            }
        });

        //NAVIGATE TO PREVIOUS PAGE
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage -= 1;
                lv.setAdapter(new ArrayAdapter<String>(GenreBeingSearched.this, android.R.layout.simple_list_item_1, p.generatePage(currentPage)));
                toggleButtons();
            }
        });
        //---------------------------------------------------------------------------------------------------------------------

        //final ListView list  = (ListView) findViewById(R.id.genreList);
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, songs);
        final int counterIntent = 50;
        final int counter = counterIntent;
        Year = "2010";
        Genre = getIntent().getStringExtra("Genre");
        Genre = "SongPointsByYear" + Genre;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        int i = 0;
        for (i = 1; i < 51; i++)
        {
            Query query = ref.child(Genre).child(Year).child((counter+i)+"");

            query.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        String Artist = "";
                        String Points = "";
                        String SongName = "";

                        for (DataSnapshot Rank : dataSnapshot.getChildren())
                        {
                            String key = Rank.getKey();
                            switch (key)
                            {
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
                        //setup(list, adapter);
                        setup(lv, adapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {

                }
            });

            Log.e("i value", i + "");
            if (i == 50)
            {
                for (int j =0; j<songs.size(); j++)
                    Log.e("SONG IN LIST",songs.get(j));

                //setup(list, adapter);
                setup(lv, adapter);
            }
        }

    }

    // Return the song list for pagination
    public ArrayList<String> getSongs()
    {
        return songs;
    }

    /* ---------------------------------------------------------------------------------------------
    TOGGLE NEXT AND PREVIOUS BUTTONS DEPENDING ON CURRENT PAGE
     */
    private void toggleButtons() {
        if (currentPage == totalPages) {
            nextBtn.setEnabled(false);
            prevBtn.setEnabled(true);
        } else if (currentPage == 0) {
            prevBtn.setEnabled(false);
            nextBtn.setEnabled(true);
        } else if (currentPage >= 1 && currentPage <= 5) {
            nextBtn.setEnabled(true);
            prevBtn.setEnabled(true);
        }
    }
    //-----------------------------------------------------------------------------------------------

    public ListView setup(ListView list, ArrayAdapter<String> adapter)
    {
        adapter = new ArrayAdapter<String>(this, R.layout.array_adapter_text_view, songs);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> adapter, View list, int pos, long id)
            {

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

// 1 ----------- Pagination vars -------------------
//private RecyclerView recyclerView;
//adapter object
//private RecyclerView.Adapter recyclerAdapter;

//progress dialog
//private ProgressDialog progressDialog;

// Load 50 songs per page
//private static final int TOTAL_ITEM_EACH_LOAD = 50;
//private int currentPage = 0;
//------------------------------------------------

// 2 ------------------------------------------
//recyclerView = (RecyclerView) findViewById(R.id.recycleView);
//recyclerView.setHasFixedSize(true);
//recyclerView.setLayoutManager(new LinearLayoutManager(this));

//progressDialog = new ProgressDialog(this);
//progressDialog.setMessage("Loading 50 More Songs");
//progressDialog.show();
//---------------------------------------------------

// 3 ----------------------------------------------
//recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//recyclerView.setAdapter(adapter);

