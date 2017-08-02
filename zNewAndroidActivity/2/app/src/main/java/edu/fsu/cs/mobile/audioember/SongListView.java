package edu.fsu.cs.mobile.audioember;

import android.app.DownloadManager;
import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by woody on 8/1/17.
 */

public class SongListView extends Fragment {
    ListView list;
    ArrayAdapter<String> adapter;
    private ArrayList<Song> songs = new ArrayList<Song>();
    private ArrayList<String> urls = new ArrayList<String>();
    private String Genre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //HEY ROB THE GENRE IS BEING PASSED HERE!!!!!!
        //HERE IT
        //IS
        //CATCH A DICK IN YOUR THROAT
        Bundle b = getArguments();
        if(b!=null)
        {
            songs = new ArrayList<Song>();
            urls = new ArrayList<String>();
            Genre = b.getString("Genre");
            Toast.makeText(getContext(),b.getString("Genre"), Toast.LENGTH_SHORT).show();
        }
        else {
            Genre = "hot-100";
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        Query query = ref.child(Genre);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot artist : dataSnapshot.getChildren()) {
                        String Artist = artist.getKey();
                        Log.i("db", Artist);
                        for (DataSnapshot song : artist.getChildren()) {
                            String Title = song.getKey();
                            Log.i("db", "  " + Title);
                            int i = 0;
                            int point = 0;
                            for (DataSnapshot p : song.getChildren()) {
                                if (i == 0){
                                    for (DataSnapshot url : p.getChildren()) {
                                        Log.i("db", "    "+url.getValue().toString());
                                        urls.add(url.getValue().toString());
                                    }
                                }
                                if (i == 1){
                                    point = Integer.parseInt(p.getValue().toString());
                                    Log.i("db", "    "+p.getValue().toString());
                                }
                                i++;
                            }
                            songs.add(new Song(Artist, Title, point, urls));
                        }
                    }
                    Collections.sort(songs, new Comparator<Song>() {
                        @Override
                        public int compare(Song o1, Song o2) {
                            return o2.getPoints() - o1.getPoints();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //NEED TO MAKE THE QUERY ASYNCH AND HAVE THE LIST WAIT TILL QUERY IS DONE
        List<String> Songs = new ArrayList<String>();
        //String[] Songs = new ;
        for(int i =0; i<songs.size(); i++){
            Songs.add((i+1) + ". " + songs.get(i).getTitle() + " by " + songs.get(i).getArtist() + "   " + songs.get(i).getPoints());
        }


        View rootView = inflater.inflate(R.layout.genre_listview, container, false);
        list = (ListView) rootView.findViewById(R.id.genre_list);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.song_listview, Songs);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos,
                                    long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), songs.get(pos).Title, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}

