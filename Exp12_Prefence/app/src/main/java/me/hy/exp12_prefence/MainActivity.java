package me.hy.exp12_prefence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    final String TAG = "TAG";
    final String PREF_NAME = "USER_INFO";

    @BindView(R.id.et_username)
    EditText etUsername;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.cb_rem)
    CheckBox cbRem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getUserInfo();
//        clearUserInfo();
    }

    private void saveUserInfo(String username, String password) {
        SharedPreferences userInfo = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();//获取Editor
        //得到Editor后，写入需要保存的数据
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();//提交修改
        Log.i(TAG, "保存用户信息成功");
    }

    private void getUserInfo() {
        SharedPreferences userInfo = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String username = userInfo.getString("username", null);
        String password = userInfo.getString("password", null);

        if (username != null && password != null) {
            etUsername.setText(username);
            etPassword.setText(password);

            Log.i(TAG, "读取用户信息");
            Log.i(TAG, "username:" + username + "， pwd:" + password);
        } else {
            Log.i(TAG, "未读取到用户信息");
        }
    }

    private void clearUserInfo(){
        SharedPreferences userInfo = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.clear();
        editor.apply();
        Log.i(TAG, "清空数据");
    }

    @OnClick(R.id.btn_login)
    void doLogin() {
        boolean rem = cbRem.isChecked();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String msg;

        if (rem) {
            saveUserInfo(username, password);
            msg = "已将用户信息存储, 用户名 " +  username + " ,密码 " + password;
        } else {
            msg = "用户名 " +  username + " ,密码 " + password;
        }

        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
