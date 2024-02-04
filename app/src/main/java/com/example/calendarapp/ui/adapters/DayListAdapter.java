package com.example.calendarapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.calendarapp.R;

import java.util.List;

public class DayListAdapter extends BaseAdapter {
    private Context context;
    private List<String> dayList;

    public DayListAdapter(Context context, List<String> dayList) {
        this.context = context;
        this.dayList = dayList;
    }

    @Override
    public int getCount() {
        return dayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate custom layout for list item
            convertView = LayoutInflater.from(context).inflate(R.layout.item_class, parent, false);

            // Initialize ViewHolder and bind views
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            // Reuse existing ViewHolder and views
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Bind data to views
        String item = dayList.get(position);
        viewHolder.textView.setText(item);

        return convertView;
    }

    // ViewHolder pattern for efficient view recycling
    private static class ViewHolder {
        TextView textView;
    }
}
