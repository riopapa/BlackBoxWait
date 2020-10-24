package com.urrecliner.blackboxwait;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    static int delaySecs = 42;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton vUp = findViewById(R.id.wait_up);
        vUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delaySecs += 11;
            }
        });
        final ImageButton vDown = findViewById(R.id.wait_down);
        vDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delaySecs -= 5;
            }
        });

//        if (getIntent().hasExtra("delay"))
//            delayTime = getIntent().getIntExtra("delay",4444);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String str = ""+delaySecs;
                        TextView tv = findViewById(R.id.delayTime);
                        tv.setText(str);
                    }
                });

                delaySecs--;
                if (delaySecs <= 0) {
                    Intent sendIntent = getPackageManager().getLaunchIntentForPackage("com.urrecliner.blackbox");
                    assert sendIntent != null;
                    startActivity(sendIntent);
                    System.exit(0);
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        }, 0, 1000);
    }

}