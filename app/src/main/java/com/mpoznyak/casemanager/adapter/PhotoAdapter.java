package com.mpoznyak.casemanager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mpoznyak.casemanager.R;
import com.mpoznyak.data.wrapper.PhotoWrapper;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<PhotoWrapper> mPhotoWrappers;
    private OnItemClickListener mListener;
    private Context mContext;

    public interface OnItemClickListener {

        void onItemClick(View v);
    }

    public PhotoAdapter(List<PhotoWrapper> photoWrappers, OnItemClickListener listener, Context context) {
        mPhotoWrappers = photoWrappers;
        mListener = listener;
        mContext = context;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_thumbnail, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        PhotoWrapper photo = mPhotoWrappers.get(position);
        Glide.with(mContext).load(photo.getPath())
                .thumbnail(0.5f)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .transition(withCrossFade())
                .into(holder.mThumbnail);
    }

    @Override
    public int getItemCount() {
        return mPhotoWrappers.size();
    }


    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView mThumbnail;


        public PhotoViewHolder(View itemView) {
            super(itemView);
            mThumbnail = itemView.findViewById(R.id.photo_thumbnail);

        }
    }
}
