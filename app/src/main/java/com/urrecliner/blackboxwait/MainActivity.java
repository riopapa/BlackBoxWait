package com.urrecliner.blackboxwait;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collection;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    static int intSecs = 181;
    String strSecs;
    private Chronometer chronometerCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        chronometerCountDown = findViewById(R.id.chronometerCountDown);

        final ImageButton vUp = findViewById(R.id.wait_up);
        vUp.setOnClickListener(v -> {
            intSecs += 61;
            strSecs = intSecs + "";
            chronometerCountDown.setText(strSecs);
        });

        final ImageButton vExit = findViewById(R.id.exit_app);
        vExit.setOnClickListener(v -> exit_application());
        Celcius.start(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        strSecs = intSecs + "";
        chronometerCountDown.setText(strSecs);
        chronometerCountDown.start();
        chronometerCountDown.setOnChronometerTickListener(chronometer -> onChronometerTickHandler());
    }

    private void onChronometerTickHandler()  {
        if(intSecs < 1) {
            exit_application();
        }
        strSecs = intSecs + "";
        chronometerCountDown.setText(strSecs);
        intSecs--;
        if (intSecs % 30 == 0) {
            TextView tv = findViewById(R.id.temperature);
            int temp = Celcius.get();
            tv.setText(temp+"");
            tv.setTextColor((temp>42) ? Color.RED: (temp>38) ? Color.YELLOW:Color.WHITE);
        }
    }

    void exit_application() {
        Intent sendIntent = getPackageManager().getLaunchIntentForPackage("com.urrecliner.blackbox");
        assert sendIntent != null;
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(sendIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    public boolean onKeyDown(final int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            intSecs += 61;
            strSecs = intSecs + "";
            chronometerCountDown.setText(strSecs);
        }
        return super.onKeyDown(keyCode, event);
    }
}