package com.example.mk.rcwindy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mk.rcwindy.Data.Spot;
import com.example.mk.rcwindy.R;

import java.util.ArrayList;

/**
 * Created by MK on 28.01.2015.
 */
public class SpotsAdapter extends ArrayAdapter<Spot> {

    ArrayList<Spot> mDisplayedValues;
    LayoutInflater inflater;


    public SpotsAdapter(Context context,int textViewResourceId,ArrayList<Spot> spots) {
        super(context, textViewResourceId,spots);


        mDisplayedValues = spots;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }



    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.spot_listlayout, null);
            holder = new ViewHolder();
            holder.SpotName = (TextView) v
                    .findViewById(R.id.SpotName);
            holder.WindSpeed = (TextView) v.findViewById(R.id.WindSpeed);

            v.setTag(holder);
        } else
            holder = (ViewHolder) v.getTag();
        holder.SpotName.setText(mDisplayedValues.get(position).getSpotName());
        holder.WindSpeed.setText(Double.toString(mDisplayedValues.get(position).getWindSpeed()));
        return v;
    }

    class ViewHolder {
        TextView SpotName;
        TextView WindSpeed;
    }
    }

