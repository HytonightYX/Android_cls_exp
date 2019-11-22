package me.hy.exp11_brtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if ("me.hsy.android.MY_BROADCAST".equals(intent.getAction())) {
            Log.e("TAG","BR接收广播" + intent.getAction());
            Toast.makeText(context,"BR接收广播  " + intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
        }

        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            Log.e("TAG", "wifiState" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    Toast.makeText(context,"wifi已经关闭", Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    Toast.makeText(context,"wifi已经打开", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
