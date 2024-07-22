package com.example.flyhunt;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartGame extends AppCompatActivity {

    GameView gameView;
    MediaPlayer bg_music;
    VideoView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /*back = findViewById(R.id.video);
        Uri uri = Uri.parse("android.resouce://" + getPackageName() + "/" + R.raw.backgroundv);
        back.setVideoURI(uri);
        back.start();
        back.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });*/
        gameView = new GameView(this);
        setContentView(gameView);
        bg_music = MediaPlayer.create(this, R.raw.gameaudio);
        if(bg_music != null){
            bg_music.start();
        }

    }

    /* @Override
    protected void onPostResume() {
        back.resume();
        super.onPostResume();
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        if(bg_music !=null){
            bg_music.stop();
            bg_music.release();
        }
    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bg_music !=null){
            bg_music.stop();
            bg_music.release();
        }
    }*/
}
