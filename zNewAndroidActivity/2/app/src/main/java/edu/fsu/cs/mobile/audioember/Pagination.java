package edu.fsu.cs.mobile.audioember;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by Kevin Williams on 12/4/2017.
 */

public class Pagination extends AppCompatActivity {
    /*
    Constants
     */
    public static final int TOTAL_NUM_ITEMS=200;
    public static final int ITEMS_PER_PAGE=50;
    public static final int ITEMS_REMAINING=TOTAL_NUM_ITEMS % ITEMS_PER_PAGE;
    public static final int LAST_PAGE=TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;

    //public GenreBeingSearched pagination;

    //public ArrayList<String> songList()
    //{
        //pagination = new GenreBeingSearched();
    //    ArrayList<String> songs = pagination.getSongs();
    //    return songs;
    //}

    //ArrayList<String> songs = new ArrayList<>();

    /*
    GENERATE PAGE DATA, PASS US CURRENT PAGE.
     */
    public ArrayList<String> generatePage(int currentPage)
    {
        int startItem=currentPage*ITEMS_PER_PAGE+1;
        int numOfData=ITEMS_PER_PAGE;

        ArrayList<String> pageData=new ArrayList<>();

        if (currentPage==LAST_PAGE && ITEMS_REMAINING>0)
        {
            for (int i=startItem;i<startItem+ITEMS_REMAINING;i++)
            {
                pageData.add("Number " + i);
                //pagination.getSongs();
                //Log.i("
            }
        }else
        {
            for (int i=startItem;i<startItem+numOfData;i++)
            {
                pageData.add("Number " + i);
                //pagination.getSongs();
            }
        }
        return pageData;
        //return pagination;
    }
}
/*
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
*/