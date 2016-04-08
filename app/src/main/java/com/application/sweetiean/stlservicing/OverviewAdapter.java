package com.application.sweetiean.stlservicing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.application.sweetiean.stlmaintenance.R;

import java.util.ArrayList;

/**
 * Created by sweetiean on 11/26/2015.
 */
public class OverviewAdapter extends BaseAdapter {
    ArrayList<String> sysaidList;
    ArrayList<String> dateList;
    ArrayList<String> taskTypeList;
    ArrayList<String> STLRepNameList;
    private Context context;
    private ArrayList<ArrayList<String>> list;

    public OverviewAdapter(Context context, ArrayList<ArrayList<String>> objects) {
        this.list = objects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.get(2).size() == 0 ? 1 : list.get(2).size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        sysaidList = list.get(0);
        taskTypeList = list.get(1);
        STLRepNameList = list.get(2);
        dateList = list.get(3);
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_display_overview, null);

        }
        placeHolder.date = (TextView) v.findViewById(R.id.dateTextView);
        placeHolder.sysaid = (TextView) v.findViewById(R.id.sysaidIdTextView);
        placeHolder.tasktype = (TextView) v.findViewById(R.id.taskTypeTextView);
        placeHolder.stlRepName = (TextView) v.findViewById(R.id.STLNameTextView);


        placeHolder.date.setText(dateList.get(position));
        placeHolder.sysaid.setText(sysaidList.get(position));
        placeHolder.tasktype.setText(taskTypeList.get(position));
        placeHolder.stlRepName.setText(STLRepNameList.get(position));
        return v;
    }

    public static class placeHolder {
        public static TextView date;
        public static TextView sysaid;
        public static TextView tasktype;
        public static TextView stlRepName;
    }

    public ArrayList getsysaidId(){
        return list.get(0);
    }
}
