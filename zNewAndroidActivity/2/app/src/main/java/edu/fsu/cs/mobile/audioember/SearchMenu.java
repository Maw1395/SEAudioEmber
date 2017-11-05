package edu.fsu.cs.mobile.audioember;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchMenu extends AppCompatActivity {
    private static int splashScreenTimer = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_menu);
        new Handler().postDelayed(new Runnable()
        {
            @Override

            public void run(){
                Intent loadingScreen = new Intent(SearchMenu.this, FrontPage.class);
                startActivity(loadingScreen);
                finish();
            }

        },splashScreenTimer);
    }
}
