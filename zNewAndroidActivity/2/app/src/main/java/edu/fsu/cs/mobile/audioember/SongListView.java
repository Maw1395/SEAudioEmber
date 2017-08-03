package edu.fsu.cs.mobile.audioember;

import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.BottomNavigationView;
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

import static android.R.string.copy;


/**
 * Created by woody on 8/1/17.
 */

public class SongListView extends Fragment {
    ListView list;
    ArrayAdapter<String> adapter;
    private ArrayList<Song> songs = new ArrayList<Song>();
    private ArrayList<String> urls = new ArrayList<String>();
    private List<String> Songs = new ArrayList<String>();
    private String Genre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.genre_listview, container, false);
        list = (ListView) rootView.findViewById(R.id.genre_list);

        Bundle b = getArguments();
        if(b!=null)
        {
            songs = new ArrayList<Song>();
            urls = new ArrayList<String>();
            Genre = b.getString("Genre");
            ((FrontPage) getActivity()).setActionBarTitle(Genre);
        }
        else {
            songs = new ArrayList<Song>();
            urls = new ArrayList<String>();
            Genre = "hot-100";
            // Set title bar
            ((FrontPage) getActivity())
                    .setActionBarTitle("hot-100");
        }

        //querying the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        //start with the genre
        Query query = ref.child(Genre);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //get the artist
                    for (DataSnapshot artist : dataSnapshot.getChildren()) {
                        String Artist = artist.getKey();
                        Log.i("db", Artist);
                        //get the song from that artist
                        for (DataSnapshot song : artist.getChildren()) {
                            String Title = song.getKey();
                            Log.i("db", "  " + Title);
                            int i = 0;
                            int point = 0;
                            for (DataSnapshot p : song.getChildren()) {
                                if (i == 0){
                                    //get the graph urls
                                    for (DataSnapshot url : p.getChildren()) {
                                        Log.i("db", "    "+url.getValue().toString());
                                        urls.add(url.getValue().toString());
                                    }
                                }
                                if (i == 1){
                                    //get the point value
                                    point = Integer.parseInt(p.getValue().toString());
                                    Log.i("db", "    "+p.getValue().toString());
                                }
                                i++;
                            }
                            Song s = new Song(Artist, Title, point);
                            for(String url : urls){
                                s.addGraph(url);
                            }
                            songs.add(s);
                            urls.clear();
                        }
                    }
                    Collections.sort(songs, new Comparator<Song>() {
                        @Override
                        public int compare(Song o1, Song o2) {
                            return o2.getPoints() - o1.getPoints();
                        }
                    });
                }
                for(int i =0; i<songs.size(); i++){
                    Songs.add((i+1) + ". " + songs.get(i).getTitle() + " by " + songs.get(i).getArtist() + "   " + songs.get(i).getPoints());
                }
                setup();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    public void setup(){
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.song_listview, Songs);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos,
                                    long id) {

                //same as all the others, set the onclick listeners
                Bundle GraphPasser = new Bundle();
                GraphPasser.putString("Title", songs.get(pos).Title);
                GraphPasser.putString("Artist", songs.get(pos).getArtist());
                GraphPasser.putStringArrayList("GraphUrls", songs.get(pos).getGraphs());
                GraphPasser.putInt("Points", songs.get(pos).getPoints());

                android.app.FragmentManager fm = getFragmentManager();

                //Start fragment transaction
                FragmentTransaction ft = fm.beginTransaction();

                SongVideoGraphPage svgp = new SongVideoGraphPage();
                svgp.setArguments(GraphPasser);

                //Replace layout in activity_front_page.xml with the fragment

                ft.replace(R.id.layout, svgp);
                ft.commit();
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), songs.get(pos).Title, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

