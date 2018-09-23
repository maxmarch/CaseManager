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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mpoznyak.document_assistant.R;

import java.io.File;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MediaManagerAdapter extends RecyclerView.Adapter<MediaManagerAdapter.FileViewHolder> {

    private SparseBooleanArray mArray = new SparseBooleanArray();
    private final List<File> mFiles;
    private OnItemClickListener mListener;
    private final Context mContext;

    public MediaManagerAdapter(List<File> files, OnItemClickListener listener, Context context) {
        mFiles = files;
        mListener = listener;
        mContext = context;
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


        FileViewHolder(View v) {
            super(v);
            mLayout = v.findViewById(R.id.photoFolderMediaManager);
            mIcon = v.findViewById(R.id.fileIconMediaManager);
            mName = v.findViewById(R.id.fileNameMediaManager);

        }
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mediamanager,
                parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FileViewHolder viewHolder, int position) {

        File file = mFiles.get(position);
        if (mFiles.get(position).isDirectory()) {
            viewHolder.mIcon.setImageResource(R.drawable.ic_folder_file_manager);
            viewHolder.mIcon.requestLayout();
            viewHolder.mIcon.setPadding(16, 16, 16, 16);
        } else {
            Glide.with(mContext).load(file.getPath())
                    .thumbnail(0.5f)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .transition(withCrossFade())
                    .into(viewHolder.mIcon);
        }

        viewHolder.mLayout.setOnClickListener(v -> mListener.onItemClick(v));

        if (mArray.get(position)) {
            viewHolder.mIcon.setColorFilter(mContext.getResources().getColor(R.color.colorSelectedItem));
        } else {
            viewHolder.mIcon.setColorFilter(mContext.getResources().getColor(android.R.color.transparent));
        }
        String name = file.getName();
        if (name.length() > 20) {
            name = name.substring(0, 16) + "...";
        }
        viewHolder.mName.setText(name);
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

}
