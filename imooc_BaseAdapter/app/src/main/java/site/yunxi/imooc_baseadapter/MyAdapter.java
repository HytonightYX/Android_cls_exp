package site.yunxi.imooc_baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import site.yunxi.imooc_baseadapter.bean.Bean;

public class MyAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Bean> mData;

    public MyAdapter(Context context, List<Bean> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 私有内部类
    private class ViewHolder {
        TextView mTitle;
        TextView mDesc;
        TextView mTime;
        TextView mPhone;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_view, parent, false);
            holder = new ViewHolder();

            convertView.setTag(holder);
        }

        return null;
    }
}
