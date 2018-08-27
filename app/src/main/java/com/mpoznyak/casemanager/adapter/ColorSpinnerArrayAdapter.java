package com.mpoznyak.casemanager.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.domain.model.Color;

import java.util.List;


public class ColorSpinnerArrayAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<Color> mColors;
    private int mResource;
    private LayoutInflater mLayoutInflater;

    public ColorSpinnerArrayAdapter(@NonNull Context context, @LayoutRes int resource,
                                    @NonNull List objects) {
        super(context, resource, 0, objects);
        mContext = context;
        mColors = objects;
        mResource = resource;
        mLayoutInflater = LayoutInflater.from(context);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {

        final View view = mLayoutInflater.inflate(mResource, parent, false);
        TextView tv = view.findViewById(R.id.color_tv);
        ImageView iv = view.findViewById(R.id.color_iv);

        Color color = mColors.get(position);

        tv.setText(color.getColorName());
        iv.setColorFilter(android.graphics.Color.parseColor(color.getHexColorCode()));

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }
}
