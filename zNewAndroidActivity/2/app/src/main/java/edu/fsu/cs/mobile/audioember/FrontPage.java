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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FrontPage extends AppCompatActivity {
    ListView songList;
    ArrayAdapter<String> adapter;
    FragmentManager fm;
    FragmentTransaction ft;
    Fragment genreFrag;
    Fragment songListFrag;
    Fragment songVideoGraphFrag;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.front_page_song_selector:
                    ft = fm.beginTransaction();
                    ft.replace(R.id.layout, songListFrag);

                    //Commit transaction
                    ft.commit();
                    return true;

                case R.id.front_page_graph_selector:
                    ft = fm.beginTransaction();
                    ft.replace(R.id.layout,songVideoGraphFrag );

                    //Commit transaction
                    ft.commit();
                    return true;

                case R.id.front_page_genre_selector:
                    ft = fm.beginTransaction();
                    ft.replace(R.id.layout, genreFrag);

                    //Commit transaction
                    ft.commit();
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
         fm = getFragmentManager();

        //Start fragment transaction
        ft = fm.beginTransaction();

        genreFrag = new GenreListview();
        songListFrag = new SongListView();
        songVideoGraphFrag = new SongVideoGraphPage();

        //Replace layout in activity_front_page.xml with the fragment
        //ft.replace(R.id.layout, genreFrag);

        //Commit transaction
        //ft.commit();

        // Sets the text for the action bar title

        //Change this
        //setActionBarTitle("Hot 100");

        //Set up the navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void setActionBarTitle(String g) {
        //set action bar depending on which genre has been selected
        switch (g){

            //Altered the genere names -KW

            //Home will eventually be the home screen that the app defaults to.  Still working on
            //what will be included in the home screen
            case "Home": getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.genre_actionbar_home);
                break;
            case "Hot 100": getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.genre_actionbar_hot_100);
                break;
            case "Pop": getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.genre_actionbar_pop);
                break;
            case "Country": getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.genre_actionbar_country);
                break;
            case "Rock": getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.genre_actionbar_rock);
                break;
            case "Dance/Electronic": getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.genre_actionbar_dance_electric);
                break;
            case "R&B/Hip-Hop": getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(R.layout.genre_actionbar_rb_hiphop);
                break;
        }


    }


    public void setActionBarTitleG() {
        // set pick genre action bar, should probably be moved into function above
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.genre_actionbar_home);
    }

    public void setActionBarSongGraph(String Title)
    {
        TextView title = getSupportActionBar().getCustomView().findViewById(R.id.genre_actionbar);
        title.setText(Title);
        //getSupportActionBar().setTitle(Title);
    }

}
