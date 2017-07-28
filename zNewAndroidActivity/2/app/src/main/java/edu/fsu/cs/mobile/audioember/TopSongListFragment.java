package edu.fsu.cs.mobile.audioember;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by woody on 7/28/17.
 */

public class TopSongListFragment extends Fragment {

   /* public TopSongListFragment() {
    }
    String newWebsite="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle!=null)
        {
            String Website = bundle.getString("msg");
            if(Website!=null) {
                Toast.makeText(getContext(),Website,Toast.LENGTH_SHORT);
                newWebsite=Website;
            }
        }

        // TODO: Handle new url if received in SmsReceiver
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_url_list, container, false);
        ListView URLS = (ListView) view.findViewById(R.id.ListOfUrls);


        String[] URL2= getResources().getStringArray(R.array.default_urls);
        List<String> URL1= Arrays.asList(URL2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, URL1);
        if (newWebsite!="")
        {
            adapter.add(newWebsite);
        }
        URLS.setAdapter(adapter);
        URLS.setClickable(true);



        URLS.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = parent.getItemAtPosition(position);
                String str = (String)
               /*Bundle bundle = new Bundle();
               bundle.getString("str", str);
               Intent myIntent = new Intent(view.getContext(), MyWebFragment.class);
               myIntent.putExtras(bundle);
               startActivity(myIntent);*//*
                Intent i = new Intent(view.getContext(),MainActivity.class);
                i.putExtra("str", str);
                startActivity(i);
            }


        });
        return view;
    }

*/
    /*
    <?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:name="edu.fsu.cs.mobile.hw3.UrlListFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_marginLeft="16dp"
    android:layout_marginRight="16dp"
    tools:context="edu.fsu.cs.mobile.hw3.UrlListFragment">

    <ListView
        android:id="@+id/ListOfUrls"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        />
</RelativeLayout>
*/
    /* THE URL LIST FRAGMENT XML^
     */
    /* HOW IT WAS originallyPLACE IN THE MAIN XML BELOW

      <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:tag="UrlListFragment"
        android:id="@+id/List"
        android:name="edu.fsu.cs.mobile.hw3.UrlListFragment"/>
     */

}
