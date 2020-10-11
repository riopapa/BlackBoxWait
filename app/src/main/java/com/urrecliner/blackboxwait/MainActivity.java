package com.urrecliner.blackboxwait;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    static int delaySecs = 40;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (getIntent().hasExtra("delay"))
//            delayTime = getIntent().getIntExtra("delay",4444);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = findViewById(R.id.delayTime);
                        tv.setText("I will be back in "+ delaySecs +" secs.");
                    }
                });

                delaySecs--;
                if (delaySecs <= 0) {
                    Intent sendIntent = getPackageManager().getLaunchIntentForPackage("com.urrecliner.blackbox");
                    assert sendIntent != null;
                    startActivity(sendIntent);
                    System.exit(0);
                }
            }
        }, 0, 1000);
    }
}