package edu.fsu.cs.mobile.songdatabase;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets the text for the action bar title
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_main);

        // Listview for main activity options
        listView = (ListView) findViewById(R.id.main_listview);

        // Get main activity options and make them clickable
        String[] mainActivitySearchOptions = getResources().getStringArray(R.array.main_listview_items);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mainActivitySearchOptions));
        //listView.setOnItemClickListener(this);
        //this.setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_main, mainActivitySearchOptions));
    }
}
