package me.hy.servicecomponent;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MusicService extends Service {
    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        //当执行完了onCreate后，就会执行onBind把操作歌曲的方法返回
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //这里只执行一次，用于准备播放器
        player = MediaPlayer.create(this, R.raw.example);
        Log.e("服务", "准备播放音乐");
    }

    //该方法包含关于歌曲的操作
    public class MyBinder extends Binder {

        //判断是否处于播放状态
        public boolean isPlaying() {
            return player.isPlaying();
        }

        public void play() {
            if (player.isPlaying()) {
                player.pause();
                Log.e("服务", "暂停音乐");
            } else {
                player.start();
                Log.e("服务", "播放音乐");
            }
        }

        public void pause() {
            player.pause();
            Log.e("服务", "停止音乐");
        }
    }
}
