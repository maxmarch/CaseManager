package com.mpoznyak.document_assistant.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpoznyak.document_assistant.R;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class FileManagerAdapter extends RecyclerView.Adapter<FileManagerAdapter.FileViewHolder> {


    private SparseBooleanArray mArray = new SparseBooleanArray();
    private final List<File> mFiles;
    private OnItemClickListener mListener;
    private final Context mContext;

    public FileManagerAdapter(Context context, List<File> files, OnItemClickListener listener) {
        mContext = context;
        mFiles = files;
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v);
    }

    public void removeListener() {
        mListener = null;
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout mLayout;
        private final ImageView mIcon;
        private final TextView mName;
        private final TextView mDate;
        private final TextView mSize;


        FileViewHolder(View v) {
            super(v);
            mLayout = v.findViewById(R.id.fileItemLayout);
            mIcon = v.findViewById(R.id.fileIcon);
            mName = v.findViewById(R.id.fileName);
            mDate = v.findViewById(R.id.fileDate);
            mSize = v.findViewById(R.id.fileSize);
        }
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filemanager,
                parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FileViewHolder viewHolder, int position) {

        File file = mFiles.get(position);
        if (mFiles.get(position).isDirectory()) {
            viewHolder.mIcon.setImageResource(R.drawable.ic_folder_file_manager);
        } else {
            viewHolder.mIcon.setImageResource(R.drawable.ic_document_file_manager);
        }

        viewHolder.mLayout.setOnClickListener(v -> mListener.onItemClick(v));
        String name = file.getName();
        if (name.length() > 21) {
            name = name.substring(0, 21) + "...";
        }
        viewHolder.mName.setText(name);
        viewHolder.mDate.setText(getLastModifiedDate(file));
        long size = file.length();
        String sizeText = size / 1024 + " Kb";
        if (size > 600000) {
            if ((size / 1024 / 1024) < 1) {
                double sizeD = size / 1024 / 1024;
                sizeD = BigDecimal.valueOf(sizeD)
                        .setScale(2, BigDecimal.ROUND_HALF_UP)
                        .doubleValue();
                sizeText = sizeD + " Mb";
            } else {
                sizeText = size / 1024 / 1024 + " Mb";
            }
        }
        if (size > 600000000) {
            sizeText = size / 1024 / 1024 / 1024 + " Gb";
        }
        viewHolder.mSize.setText(sizeText);
        if (mArray.get(position)) {
            viewHolder.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorSelectedItem));
        } else {
            viewHolder.mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorBackground));
        }
    }

    public SparseBooleanArray getSparseBooleanArray() {
        return mArray;
    }

    public void refreshSparseBooleanArray() {
        mArray = new SparseBooleanArray();
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    private String getLastModifiedDate(File file) {
        long millis = file.lastModified();
        SimpleDateFormat pattern = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        pattern.setTimeZone(TimeZone.getDefault());
        return pattern.format(millis);
    }
}
