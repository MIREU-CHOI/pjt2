package com.example.study03;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(new SampleView(this));

        mp = MediaPlayer.create(this, R.raw.jinglebell);
        mp.setLooping(false);

        tv = this.findViewById(R.id.helloBtn);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mp.isPlaying()){
                    mp.start();
                    tv.setText("Stop");
                }else {
                    mp.pause();
                    tv.setText("Start");
                }
            }
        });
    }
}