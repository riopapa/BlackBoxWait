package com.urrecliner.blackboxwait;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    static int delaySecs = 182;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        final ImageButton vUp = findViewById(R.id.wait_up);
        vUp.setOnClickListener(v -> {
//            if (keyNowTime == 0)        // remote con does duplicated action ?
                delaySecs += 62;
        });
        final ImageButton vExit = findViewById(R.id.exit_app);
        vExit.setOnClickListener(v -> exit_application());

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    String str = ""+delaySecs;
                    TextView tv = findViewById(R.id.delayTime);
                    tv.setText(str);
                });

                delaySecs--;
                if (delaySecs < 0)
                    exit_application();
            }
        }, 0, 1000);
    }

    void exit_application() {
        Intent sendIntent = getPackageManager().getLaunchIntentForPackage("com.urrecliner.blackbox");
        assert sendIntent != null;
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(sendIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    static long keyOldTime = 0, keyNowTime = 0;
    @Override
    public boolean onKeyDown(final int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
//            keyNowTime = System.currentTimeMillis();
//            if (keyNowTime > keyOldTime + 800) {
//                keyOldTime = keyNowTime;
//                new Timer().schedule(new TimerTask() {
//                    public void run() {
//                        if (keyNowTime == keyOldTime)
                            delaySecs += 62;
//                    }
//                }, 1000);
//            }
        }
        return super.onKeyDown(keyCode, event);
    }
}