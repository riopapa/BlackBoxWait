package com.riopapa.blackwait;

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

        final ImageButton vReturn = findViewById(R.id.return2box);
        vReturn.setOnClickListener(v -> reLoad_BlackBox());

        final ImageButton vExit = findViewById(R.id.exit_app);
        vExit.setOnClickListener(v -> exit_app());

        Celcius.start(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        strSecs = intSecs + "";
        chronometerCountDown.setText(strSecs);
        chronometerCountDown.start();
        chronometerCountDown.setOnChronometerTickListener(chronometer -> onChronometerTickHandler());
        showCelcius();
    }

    private void onChronometerTickHandler()  {
        if(intSecs < 1) {
            reLoad_BlackBox();
        }
        strSecs = intSecs + "";
        chronometerCountDown.setText(strSecs);
        intSecs--;
        if (intSecs % 20 == 0) {
            showCelcius();
        }
    }

    private void showCelcius() {
        TextView tv = findViewById(R.id.temperature);
        int celcius = Celcius.get();
        tv.setText(celcius+"");
        tv.setTextColor((celcius<37)? Color.WHITE:((celcius<42)? Color.YELLOW:Color.RED));
    }

    void reLoad_BlackBox() {
        Intent sendIntent = getPackageManager().getLaunchIntentForPackage("com.riopapa.blackbox");
        assert sendIntent != null;
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(sendIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    void exit_app() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    long keyOldTime = 0, keyNowTime = 0;
    @Override
    public boolean onKeyDown(final int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            intSecs += 61;
            strSecs = intSecs + "";
            chronometerCountDown.setText(strSecs);
            keyNowTime = System.currentTimeMillis();
            if (keyOldTime == 0) {  // first Time
                keyOldTime = keyNowTime;
            } else if (keyNowTime - keyOldTime < 2000 && keyNowTime - keyOldTime > 300) {
                reLoad_BlackBox();
            } else
                keyOldTime = 0;
        }
        return super.onKeyDown(keyCode, event);
    }
}