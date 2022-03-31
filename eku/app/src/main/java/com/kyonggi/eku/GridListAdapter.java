package com.kyonggi.eku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridListAdapter extends BaseAdapter {
    ArrayList<ListItem> items = new ArrayList<ListItem>();
    Context context;

    public void addItem(ListItem item){
        items.add(item);
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext(); //context정보 가져오기
        ListItem listItem = items.get(position); //item 불러오기

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate((R.layout.list_item), parent, false);
        }

        TextView content = convertView.findViewById(R.id.contentView);
        content.setText(listItem.getContent());

        TextView time = convertView.findViewById(R.id.timeView);
        time.setText(listItem.getTime());


        return convertView;
    }
}
