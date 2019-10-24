package me.hy.exp6_menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle;
    ListView listView;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTitle = findViewById(R.id.tv_title);
        listView = findViewById(R.id.listview);
        setButton();
        showListView();
    }

    private void setButton() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, v);//第二个参数是绑定的那个view
                //获取菜单填充器
                MenuInflater inflater = popup.getMenuInflater();
                //填充菜单
                inflater.inflate(R.menu.popup_menu, popup.getMenu());
                //绑定菜单项的点击事件
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_color_red:
                                tvTitle.setTextColor(Color.rgb(255, 0, 0));
                                break;
                            case R.id.menu_color_green:
                                tvTitle.setTextColor(Color.rgb(0, 255, 0));
                                break;
                            case R.id.menu_color_blue:
                                tvTitle.setTextColor(Color.rgb(0, 0, 255));
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu radioMenu = new PopupMenu(MainActivity.this, v);//第二个参数是绑定的那个view
                //获取菜单填充器
                MenuInflater inflater = radioMenu.getMenuInflater();
                //填充菜单
                inflater.inflate(R.menu.popup_radio_menu, radioMenu.getMenu());
                //绑定菜单项的点击事件
                radioMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        item.setChecked(!item.isChecked());
                        switch (item.getItemId()) {
                            case R.id.menu_radio_traffic:
                                tvTitle.setText("traffic");
                                return true;
                            case R.id.menu_radio_map:
                                tvTitle.setText("map");
                                return true;
                            case R.id.menu_radio_satellite:
                                tvTitle.setText("satellite");
                                return true;
                            default:
                                return MainActivity.super.onOptionsItemSelected(item);
                        }
                    }
                });
                radioMenu.show();
            }
        });
    }



    /**
     * 设置listview的显示内容
     */
    private void showListView() {
        ListView listview = findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getData());
        listview.setAdapter(adapter);
        this.registerForContextMenu(listview);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        Log.d("CreateMenu", "onCreateContextMenu");
        super.onCreateContextMenu(menu, v, menuInfo);
        //设置Menu显示内容
        menu.setHeaderTitle("选个颜色吧");
        menu.setHeaderIcon(R.drawable.ic_launcher_foreground);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String itemName = list.get((int) info.id);
        switch (item.getItemId()) {
            case R.id.context_menu_red:
                tvTitle.setText(itemName + "红色");
                break;

            case R.id.context_menu_green:
                tvTitle.setText(itemName + "绿色");
                break;

            case R.id.context_menu_blue:
                tvTitle.setText(itemName + "蓝色");
                break;
        }

        return super.onContextItemSelected(item);

    }

    private ArrayList<String> getData() {
        list = new ArrayList<>();
        list.add("我喜欢");
        list.add("我讨厌");
        list.add("我不在乎");
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("CreateMenu", "onCreateOptionsMenu");

        menu.add(menu.NONE, 1, 1, "菜单1").setIcon(R.drawable.menu);
        menu.add(menu.NONE, 2, 2, "菜单2").setIcon(R.drawable.menu);
        menu.add(menu.NONE, 3, 3, "菜单3").setIcon(R.drawable.menu);
        menu.add(menu.NONE, 4, 4, "菜单4").setIcon(R.drawable.menu);
        menu.add(menu.NONE, 5, 5, "菜单5").setIcon(R.drawable.menu);
        menu.add(menu.NONE, 6, 6, "菜单6").setIcon(R.drawable.menu);
        menu.add(menu.NONE, 7, 7, "菜单7").setIcon(R.drawable.menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//菜单选择监听
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(MainActivity.this, "点击菜单 1", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(MainActivity.this, "点击菜单 2", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(MainActivity.this, "点击菜单 3", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(MainActivity.this, "点击菜单 4", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(MainActivity.this, "点击菜单 5", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(MainActivity.this, "点击菜单 6", Toast.LENGTH_SHORT).show();
                break;
            case 7:
                Toast.makeText(MainActivity.this, "点击菜单 7", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}

/*
onCreateOptionsMenu(Menu menu)
每次Activity一创建就会执行，一般只执行一次，创建并保留Menu的实例；

onPrepareOptionsMenu(Menu menu)
每次menu被打开时，该方法就会执行一次，可用于对传入的旧Menu对象进行修改操作；

onOptionsItemSelected(MenuItem item)
每次menu菜单项被点击时，该方法就会执行一次；

invalidateOptionsMenu()
刷新menu里的选项里内容，它会调用onCreateOptionsMenu(Menu menu)方法

onCreateContextMenu()
创建控件绑定的上下文菜单menu，根据方法里的View参数识别是哪个控件绑定

onContextItemSelected(MenuItem item)
点击控件绑定的上下菜单menu的内容项

invalidateOptionsMenu()
通知系统刷新Menu，之后，onPrepareOptionsMenu会被调用
 */