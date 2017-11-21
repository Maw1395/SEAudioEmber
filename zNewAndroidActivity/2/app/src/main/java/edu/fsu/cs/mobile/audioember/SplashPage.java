package edu.fsu.cs.mobile.audioember;
//https://stackoverflow.com/questions/43598734/android-app-opens-after-a-few-seconds-after-pressing-home-button-during-splash-s
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
        
public class SplashPage extends AppCompatActivity
 {
     private static int splashScreenTimer = 4000;
     private long splashScreenDelay;
     private Handler splashScreenPause;
        
            @Override
     protected void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_splash_page);
                splashScreenPause = new Handler();
            }
        
            @Override
     protected void onResume()
            {
                super.onResume();
                long pausedTime = System.currentTimeMillis() - splashScreenDelay;
                if(pausedTime > splashScreenTimer)
                    pausedTime = splashScreenTimer;
                splashScreenPause.postDelayed(new Runnable()
                {
                    @Override
             public void run()
                    {
                       Intent intent = new Intent(SplashPage.this, MainActivity.class);
                       startActivity(intent);
                       SplashPage.this.finish();
                    }
                }, pausedTime);
        
                splashScreenDelay = System.currentTimeMillis();
            }
        
            @Override
     protected void onPause()
            {
                super.onPause();
                splashScreenPause.removeCallbacksAndMessages(null);
            }
        }