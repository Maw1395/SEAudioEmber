package edu.fsu.cs.mobile.audioember;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Created by woody on 8/1/17.
 */

public class SongListView extends Fragment {
    ListView list;
    ArrayAdapter<String> adapter;
    String[] Songs = {"Song1", "Song2", "Song3", "Song4", "Song5", "Song6"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.genre_listview, container, false);
        list = (ListView) rootView.findViewById(R.id.genre_list);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.song_listview, Songs);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos,
                                    long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), Songs[pos], Toast.LENGTH_SHORT).show();
            }
        });
        //HEY ROB THE GENRE IS BEING PASSED HERE!!!!!!
        //HERE IT
        //IS
        //CATCH A DICK IN YOUR THROAT
        Bundle b = getArguments();
        if(b!=null)
        {
            Toast.makeText(getContext(),b.getString("Genre"), Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }
}

