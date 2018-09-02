package com.mpoznyak.casemanager.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpoznyak.casemanager.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

public class FileManagerAdapter extends RecyclerView.Adapter<FileManagerAdapter.FileViewHolder> {

    private List<File> mFiles;
    private OnItemClickListener mListener;

    public FileManagerAdapter(List<File> files, OnItemClickListener listener) {
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

        private ConstraintLayout mLayout;
        private ImageView mIcon;
        private TextView mName;
        private TextView mDate;
        private TextView mSize;

        public FileViewHolder(View v) {
            super(v);
            mLayout = v.findViewById(R.id.fileItemLayout);
            mIcon = v.findViewById(R.id.fileIcon);
            mName = v.findViewById(R.id.fileName);
            mDate = v.findViewById(R.id.fileDate);
            mSize = v.findViewById(R.id.fileSize);
        }
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filemanager_row,
                parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FileViewHolder viewHolder, int position) {

        File file = mFiles.get(position);
        if (mFiles.get(position).isDirectory()) {
            viewHolder.mIcon.setImageResource(R.drawable.ic_baseline_folder_24px);
        } else {
            viewHolder.mIcon.setImageResource(R.drawable.ic_baseline_insert_drive_file_24px);
        }

        viewHolder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v);
            }
        });
        viewHolder.mName.setText(file.getName());
        viewHolder.mDate.setText(getLastModifiedDate(file));
        viewHolder.mSize.setText(String.valueOf(file.length() / 1024));
        file = null;
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    private String getLastModifiedDate(File file) {
        long millis = file.lastModified();
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return pattern.format(millis);
    }
}
