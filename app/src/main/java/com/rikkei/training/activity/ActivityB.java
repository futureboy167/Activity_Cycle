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

public class ActivityB extends AppCompatActivity {
    OrientationEventListener mOrientationListener;ggggggggggg
    private Button btn;
    private MediaPlayer media;
    private int checkpoint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("B","create B");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        btn = this.findViewById(R.id.button1);
        media = MediaPlayer.create(this, R.raw.uotmi);
        media.start();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivityA.class);
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

    }



    @Override
    public void onDestroy() {
        Log.d("B","destroy B");
        super.onDestroy();
        media.stop();
        android.os.Debug.stopMethodTracing();
    }

    @Override
    public void onPause() {
        Log.d("B","pause B");
        super.onPause();
        media.pause();
        this.checkpoint= media.getCurrentPosition();

    }
    @Override
    public void onResume() {
        super.onResume();
        media.seekTo(this.checkpoint);
        media.start();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("B","stop B");

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("B","start B");

    }


}