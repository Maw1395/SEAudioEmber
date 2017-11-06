package edu.fsu.cs.mobile.audioember;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by woody on 7/28/17.
 */

public class GenreListview extends Fragment {
    ListView list;
    ArrayAdapter<String> adapter;

    //Altered the genre names -KW
    String [] genres = {"Hot 100", "Country", "Rock", "R&B/Hip-Hop", "Dance/Electric", "Pop"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.genre_listview, container, false);
        list = (ListView) rootView.findViewById(R.id.genre_list);

        //set the genres to the array adapted
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.song_listview, genres);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos,
                                    long id) {
                // TODO Auto-generated method stub

                //set the on click listener and launch the song-list-page with the intents
                Bundle genre_passer = new Bundle();
                genre_passer.putString("Genre", genres[pos]);
                android.app.FragmentManager fm = getFragmentManager();

                //Start fragment transaction
                FragmentTransaction ft = fm.beginTransaction();

                SongListView songListFrag = new SongListView();
                songListFrag.setArguments(genre_passer);

                //Replace layout in activity_front_page.xml with the fragment


                ft.replace(R.id.layout, songListFrag);
                ft.commit();
            }
        });

        // Set title bar
        ((FrontPage) getActivity()).setActionBarTitleG();
        return rootView;
    }


}
