package com.kyonggi.eku;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GridListAdapter extends BaseAdapter {
    /*
    *
    * 낙서저장하는 어댑터
     */
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

        context = parent.getContext();
        ListItem listItem = items.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate((R.layout.list_item), parent, false);


        TextView content = convertView.findViewById(R.id.contentView);
        content.setText(listItem.getContent());
        Random random = new Random();
        int randomColor = random.nextInt(6) + 1;
        switch (randomColor) {
            case 1:
                convertView.setBackgroundColor(Color.rgb(224,226,128));
                break;
            case 2:
                convertView.setBackgroundColor(Color.rgb(128,226,150));
                break;
            case 3:
                convertView.setBackgroundColor(Color.rgb(128,209,226));
                break;
            case 4:
                convertView.setBackgroundColor(Color.rgb(226,134,128));
                break;
            case 5:
                convertView.setBackgroundColor(Color.rgb(128,138,226));
                break;
            default:
                convertView.setBackgroundColor(Color.rgb(215,165,198));
                break;
        }

        TextView time = convertView.findViewById(R.id.timeView);
        time.setText(listItem.getTime());

        return convertView;
    }
}
