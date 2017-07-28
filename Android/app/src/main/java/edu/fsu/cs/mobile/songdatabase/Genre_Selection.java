package edu.fsu.cs.mobile.songdatabase;

/**
 * Created by Kevin Williams on 7/21/2017.
 */

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Genre_Selection extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genre_selection);

        // Sets the text for the action bar title
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_genre_selection);

        // Listview for main activity options
        listView = (ListView) findViewById(R.id.genre_listview);

        // Get main activity options and make them clickable
        String[] mainActivitySearchOptions = getResources().getStringArray(R.array.genre_listview_items);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mainActivitySearchOptions));
        //listView.setOnItemClickListener(this):
    }
}