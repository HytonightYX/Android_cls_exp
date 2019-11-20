package me.hy.servicecomponent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdateService extends Service {

    Thread t = null;

    @Override
    public void onCreate() {
        super.onCreate();
        final Runnable MyThread = new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(2000);
                        Log.i("服务", "有更新啦......");
                    } catch (InterruptedException e) {
                        Log.i("服务", "线程中断");
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };

        t = new Thread(MyThread);
        t.start();

        Log.i("服务", "更新服务 Create");
    }

    public UpdateService() {

    }

    @Override
    public void onDestroy() {
        Log.i("服务", "关闭更新服务");
        t.interrupt();
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
