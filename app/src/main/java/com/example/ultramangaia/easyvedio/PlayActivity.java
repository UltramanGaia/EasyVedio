package com.example.ultramangaia.easyvedio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class PlayActivity extends AppCompatActivity  implements View.OnClickListener{

    private VideoView videoView;
    private String path;
    private static final String TAG = "PlayActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //接受视频文件名
        Intent intent = getIntent();
        path = intent.getStringExtra("path");

       //创建控件
        videoView = (VideoView)findViewById(R.id.vedioview);
        Button play = (Button) findViewById(R.id.play);
        Button pause = (Button) findViewById(R.id.pause);
        Button replay = (Button) findViewById(R.id.replay);
        //按钮的点击监听器
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);

        initVideoPath();

    }

    private void initVideoPath(){

        //File file = new File(Environment.getExternalStorageDirectory(),"0MY_AREA/奇异博士.HDTS中英双字.mp4");
        videoView.setVideoPath(path);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.play:
                if(!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.pause:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.replay:
                if(videoView.isPlaying()){
                    videoView.resume();
                }
                break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(videoView!=null)
            videoView.suspend();
    }
}
