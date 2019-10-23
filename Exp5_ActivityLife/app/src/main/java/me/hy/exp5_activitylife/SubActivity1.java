package me.hy.exp5_activitylife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SubActivity1 extends AppCompatActivity {

    TextView tvSubUserName;
    TextView tvSubPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub1_layout);

        tvSubUserName = findViewById(R.id.tv_sub1_username);
        tvSubPwd = findViewById(R.id.tv_sub1_pwd);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        assert bundle != null;
        String username = bundle.getString("username");
        String pwd = bundle.getString("pwd");

        tvSubUserName.setText(username);
        tvSubPwd.setText(pwd);
    }
}
