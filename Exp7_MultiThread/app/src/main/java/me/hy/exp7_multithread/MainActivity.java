package me.hy.exp7_multithread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Thread thread;
    Button btnAsyncTask;
    ProgressBar progressBar;
    TextView tvPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        progressBar = findViewById(R.id.progressBar);
        tvPercent = findViewById(R.id.tv_percent);
        btnAsyncTask = findViewById(R.id.btn_async_task);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long ret = 0;
                for (int i = 1; i <= 50000; i++) {
                    ret += i;
                }
                editText.setText(String.valueOf(ret));
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread = new Thread(MyThread);
                thread.start();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.interrupt();
            }
        });

        findViewById(R.id.btn_async_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new MyTask().execute("https://baidu.com");
                new MyTask().execute("https://yunxi.site");
            }
        });
    }

    class MyTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvPercent.setText("异步开始");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String mUrl = params[0];

            Log.i("BAIDU", "doInBackground  " + mUrl);

            try {
                URL url = new URL(mUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Accept-Encoding", "identity");
                conn.connect();

                int total = conn.getContentLength();
                Log.i("BAIDU", "total=" + total);
                InputStream in = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];

                int count = 0;
                int length = -1;
                while ((length = in.read(buf)) != -1) {
                    baos.write(buf, 0, length);
                    count += length;
                    Log.i("BAIDU", "" + ((count / (float) total) * 100));
                    publishProgress((int) ((count / (float) total) * 100));
                    Thread.sleep(100);
                }

                conn.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i("BAIDU", "onProgressUpdate  " + values[0]);
            progressBar.setProgress(values[0]);
            tvPercent.setText("Load " + values[0] + "%");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            tvPercent.setText("下载结束");
        }
    }

    final Runnable MyThread = new Runnable() {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(2000);

                    long ret = 0;
                    for (int i = 1; i <= 3000; i++) {
                        ret += i;
                    }

                    Log.i("THREAD", "Thread is running, ret=" + ret);
                } catch (InterruptedException e) {
                    Log.i("THREAD", "Interrupted Exception");
                    e.printStackTrace();
                    break;
                }
            }
        }
    };
}
