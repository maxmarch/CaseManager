package com.mpoznyak.casemanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.domain.model.Type;

import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeViewHolder> {

    private Context mContext;
    private List<Type> mTypes;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public TypeAdapter(List<Type> types, OnItemClickListener listener) {
        mListener = listener;
        mTypes = types;
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mName;

        private TypeViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v);
                }
            });
            mImageView = (ImageView) view.findViewById(R.id.circle_drawable);
            mName = (TextView) view.findViewById(R.id.type_name);
        }

    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_type, parent, false);
        return new TypeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TypeViewHolder vh, int position) {
        Type type = mTypes.get(position);
        vh.mName.setText(type.getName());
        vh.mImageView.setColorFilter(Color.parseColor(type.getColor()));

    }

    @Override
    public int getItemCount() {
        return mTypes.size();
    }


}
