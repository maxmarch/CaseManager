package com.mpoznyak.document_assistant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpoznyak.document_assistant.R;
import com.mpoznyak.domain.model.Type;

import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeViewHolder> {

    private Context mContext;
    private final List<Type> mTypes;
    private final OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public TypeAdapter(List<Type> types, OnItemClickListener listener) {
        mListener = listener;
        mTypes = types;
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageView;
        private final TextView mName;

        private TypeViewHolder(View view) {
            super(view);
            view.setOnClickListener(v -> mListener.onItemClick(v));
            mImageView = view.findViewById(R.id.circle_drawable);
            mName = view.findViewById(R.id.type_name);
        }

    }

    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_type_main, parent, false);
        return new TypeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TypeViewHolder vh, int position) {
        Type type = mTypes.get(position);
        String typeName = type.getName();
        if (typeName.length() > 20) {
            typeName = typeName.substring(0, 20) + "...";
        }
        vh.mName.setText(typeName);
        vh.mImageView.setColorFilter(Color.parseColor(type.getColor()));

    }

    @Override
    public int getItemCount() {
        return mTypes.size();
    }


}
