package com.rikkei.training.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;

public class ActivityA extends AppCompatActivity {
    OrientationEventListener mOrientationListener;
    private Button btn ;
    private MediaPlayer media;
    private int checkpoint = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = this.findViewById(R.id.button);
        media = MediaPlayer.create(this, R.raw.lemon);
        Log.d("A","create A");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityB.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(intent, 0);
            }
        });

        mOrientationListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int orientation) {
                media.reset();
                media.start();
                Log.v("A",
                        "Orientation changed to " + orientation);

            }
        };

        if (mOrientationListener.canDetectOrientation() == true) {
            Log.v("A", "Can detect orientation");
            mOrientationListener.enable();
        } else {
            Log.v("A", "Cannot detect orientation");
            mOrientationListener.disable();
        }

        media.start();
    }


    @Override
    public void onDestroy() {
        Log.d("A","destroy A");
        super.onDestroy();
        media.stop();
        android.os.Debug.stopMethodTracing();
    }

    @Override
    public void onPause() {
        Log.d("A","destroy A");
        super.onPause();
        media.pause();
        this.checkpoint= media.getCurrentPosition();

    }
    @Override
    public void onResume() {
        Log.d("A","resume A");
        super.onResume();
        media.seekTo(this.checkpoint);
        media.start();

    }
    @Override
    protected void onStop() {
        Log.d("A","stop A");
        super.onStop();

    }
    @Override
    protected void onStart() {
        Log.d("A","start A");
        super.onStart();

    }


}