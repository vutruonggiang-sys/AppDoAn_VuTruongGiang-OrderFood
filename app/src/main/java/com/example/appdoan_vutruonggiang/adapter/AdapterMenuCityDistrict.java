package com.example.appdoan_vutruonggiang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.inteface.IAdapterListener;

import java.util.List;

public class AdapterMenuCityDistrict extends ArrayAdapter<String> {

    List<String> stringList;
    IAdapterListener iAdapterListener;
    int itemLayout;

    public AdapterMenuCityDistrict(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.stringList=objects;
        this.itemLayout=resource;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }
        TextView strName = (TextView) view.findViewById(R.id.tvItemCityDistrict);
        strName.setText(getItem(position));
        if (iAdapterListener != null) {
            view.setOnClickListener(v -> iAdapterListener.onClickListerAdapter(position,v));
        }
        return view;
    }
}
