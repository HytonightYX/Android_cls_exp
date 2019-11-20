package me.hy.servicecomponent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MyConnection conn;
    private MusicService.MyBinder musicControl;

    private Button btnMusicOpen;
    private Button btnMusicClose;
    private static final int UPDATE_PROGRESS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMusicOpen = findViewById(R.id.btn_open_music);
        btnMusicClose = findViewById(R.id.btn_close_music);

        Intent intent3 = new Intent(this, MusicService.class);
        conn = new MyConnection();

        bindService(intent3, conn, BIND_AUTO_CREATE);

        findViewById(R.id.btn_open_update).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("服务", "按下按钮");

                Intent i = new Intent(MainActivity.this, UpdateService.class);
                startService(i);
            }
        });

        findViewById(R.id.btn_close_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("服务", "停止按钮");
                Intent i = new Intent(MainActivity.this, UpdateService.class);
                stopService(i);
            }
        });

        findViewById(R.id.btn_open_intent_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("服务", "MainActivity 线程：" + Thread.currentThread().getId());
                Intent i = new Intent(MainActivity.this, MyIntentService.class);
                startService(i);
            }
        });
    }

    private class MyConnection implements ServiceConnection {

        //服务启动完成后会进入到这个方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获得service中的MyBinder
            musicControl = (MusicService.MyBinder) service;
            //更新按钮的文字
            updatePlayText();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出应用后与service解除绑定
        unbindService(conn);
    }

    //更新按钮的文字
    public void updatePlayText() {
        if (musicControl.isPlaying()) {
            btnMusicOpen.setText("正在播放");
        } else {
            btnMusicOpen.setText("打开音乐服务");
        }
    }

    //调用MyBinder中的play()方法
    public void play(View view) {
        Log.e("服务", "开始按钮按下");
        musicControl.play();
        updatePlayText();
    }

    //调用MyBinder中的play()方法
    public void stop(View view) {
        Log.e("服务", "停止按钮按下");
        musicControl.pause();
        updatePlayText();
    }
}
