package edu.fsu.cs.mobile.audioember;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class FrontPage extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.front_page_song_selector:

                    return true;
                case R.id.front_page_artist_selector:

                    return true;
                case R.id.front_page_genre_selector:

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        //Fragment manager associcated with this activity
        FragmentManager fm = getFragmentManager();

        //Start fragment transaction
        FragmentTransaction ft = fm.beginTransaction();

        Fragment genreFrag = new GenreListview();

        //Replace layout in activity_front_page.xml with the fragment
        ft.replace(R.id.layout, genreFrag);

        //Commit transaction
        ft.commit();

        // Sets the text for the action bar title
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.genre_actionbar);

        //Set up the navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
