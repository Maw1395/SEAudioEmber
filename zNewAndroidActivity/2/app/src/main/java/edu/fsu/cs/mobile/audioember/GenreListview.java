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
 * Created by woody on 7/28/17.
 */

public class GenreListview extends Fragment {
    ListView list;
    ArrayAdapter<String> adapter;
    String [] genres = {"Hot 100","Country","Rock","R&B", "EDM", "Pop"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.genre_listview, container,false);
        list = (ListView) rootView.findViewById(R.id.genre_list);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, genres);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos,
                                    long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), genres[pos], Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.genre_view, container, false);
    }*/
}
