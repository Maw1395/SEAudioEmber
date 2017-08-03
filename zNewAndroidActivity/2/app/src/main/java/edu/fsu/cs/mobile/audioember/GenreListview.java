package edu.fsu.cs.mobile.audioember;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by woody on 7/28/17.
 */

public class GenreListview extends Fragment {
    ListView list;
    ArrayAdapter<String> adapter;
    String [] genres = {"hot-100","country-songs","rock-songs","r-b-hip-hop-songs", "dance-electronic-songs", "pop-songs"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.genre_listview, container, false);
        list = (ListView) rootView.findViewById(R.id.genre_list);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.song_listview, genres);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos,
                                    long id) {
                // TODO Auto-generated method stub
                Bundle genrepasser = new Bundle();
                genrepasser.putString("Genre", genres[pos]);
                android.app.FragmentManager fm = getFragmentManager();

                //Start fragment transaction
                FragmentTransaction ft = fm.beginTransaction();

                SongListView songListFrag = new SongListView();
                songListFrag.setArguments(genrepasser);

                //Replace layout in activity_front_page.xml with the fragment


                ft.replace(R.id.layout, songListFrag);
                ft.commit();
            }
        });
        // Set title bar
        ((FrontPage) getActivity())
                .setActionBarTitleG();
        return rootView;
    }


}
