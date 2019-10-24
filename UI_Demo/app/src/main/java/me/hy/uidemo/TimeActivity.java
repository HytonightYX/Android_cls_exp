package me.hy.uidemo;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class TimeActivity extends AppCompatActivity {
    public static Intent getCallingIntent(Context context){
        Intent callingIntent = new Intent(context, TimeActivity.class);
        return callingIntent;
    }


}
