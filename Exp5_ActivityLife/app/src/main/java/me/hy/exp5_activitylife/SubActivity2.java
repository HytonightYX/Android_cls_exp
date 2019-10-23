package me.hy.exp5_activitylife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SubActivity2 extends AppCompatActivity {

    private ListView mainListView;
    private ArrayList<String> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub2_layout);
        mainListView = findViewById(R.id.list_view);

        listData = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            listData.add("这是第 " + (i + 1) + " 条数据");
        }

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("itemNum", position + 1 + "");
                //RESULT_OK 这个值相当于 onActivityResult方法里面的resultCode
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mainListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData));
    }
}
