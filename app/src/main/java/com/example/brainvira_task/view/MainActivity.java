package com.example.brainvira_task.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.brainvira_task.R;
import com.example.brainvira_task.Util.NetworkUtils;


public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN_DELAY = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetworkUtils.isNetworkAvailable(MainActivity.this)) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.no_change);

                } else {

                }
            }
        }, SPLASH_SCREEN_DELAY);
    }
}