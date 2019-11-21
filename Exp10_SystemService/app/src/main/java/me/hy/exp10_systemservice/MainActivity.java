package me.hy.exp10_systemservice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    TextView tvNetwork, tvWifi;
    ToggleButton toggleButton;
    private final int CAMERA_REQUEST = 8888;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    private String name = "name";

    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tvNetwork = findViewById(R.id.tv_network);
        tvWifi = findViewById(R.id.tv_wifi);
        toggleButton = findViewById(R.id.btn_wifi_toggle);

        findViewById(R.id.btn_network).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getActiveNetworkInfo();

                if (info != null) {
                    boolean bNet = info.isConnected();
                    tvNetwork.setText(bNet ? "已连接" : "未连接");
                }
            }
        });

        findViewById(R.id.btn_wifi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                tvWifi.setText(mWifi.isConnected() ? "Wi-Fi已连接" : "Wi-Fi未连接");

            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                WifiManager wfManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                Log.i("服务", "isChecked  " + isChecked);
                wfManager.setWifiEnabled(isChecked);
            }
        });

        findViewById(R.id.btn_sensor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                List<Sensor> sensorList;
                sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
                String sensorNames = "";

                for (Sensor sensor : sensorList) {
                    sensorNames += sensor.getName() + "\n";
                }

                showAlertDialog("传感器列表", sensorNames);
            }
        });
    }

    private void showAlertDialog(String title, String message) {
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)//设置标题
                .setMessage(message)//设置提示内容
                //确定按钮
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();//创建对话框
        dialog.show();//显示对话框
    }

    @OnClick(R.id.btn_camera)
    public void takePhoto() {
        Log.i("TAG", "点击拍照按钮");
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @OnClick(R.id.btn_notification)
    public void showNoti() {
        Intent intent = new Intent(this, SubActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        //第一步：获取状态通知栏管理：
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;

        //第二步：实例化通知栏构造器NotificationCompat.Builder：
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//判断API
            NotificationChannel mChannel = new NotificationChannel(default_notification_channel_id, name,
                    NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(mChannel);
        }

        notification = new NotificationCompat.Builder(this, default_notification_channel_id)
                .setContentTitle("这是一个内容标题")       //设置通知栏标题
                .setContentText("这是一个内容文本")        //设置通知栏显示内容
                .setWhen(System.currentTimeMillis())    //通知产生的时间。
                // 会在通知信息里显示，通常是系统获取到的时间
                .setSmallIcon(R.mipmap.ic_launcher)     //设置通知小ICON
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round)) //设置通知大ICON
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        //第三步：对Builder进行配置：
        manager.notify(1, notification);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}