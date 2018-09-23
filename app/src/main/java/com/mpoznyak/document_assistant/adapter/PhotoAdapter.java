package com.mpoznyak.document_assistant.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mpoznyak.data.wrapper.PhotoWrapper;
import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.util.ClickListenerOption;
import com.mpoznyak.document_assistant.view.SquareLayout;
import com.mpoznyak.document_assistant.view.fragment.SlideshowFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private final SparseBooleanArray mArray;
    private final List<PhotoWrapper> mPhotoWrappers;
    private final OnItemClickListener mClickListener;
    private final FragmentActivity mActivity;
    private ClickListenerOption mClickListenerOption;
    private Set<PhotoWrapper> mMultiSelectedPhotos;
    private int itemPosition;

    public interface OnItemClickListener {

        void onItemClick(View v);
    }


    public PhotoAdapter(List<PhotoWrapper> photoWrappers, OnItemClickListener clickListener, FragmentActivity activity) {
        mPhotoWrappers = photoWrappers;
        mClickListener = clickListener;
        mActivity = activity;
        mClickListenerOption = ClickListenerOption.DEFAULT;
        mMultiSelectedPhotos = new HashSet<>();
        mArray = new SparseBooleanArray();
    }

    public void setClickListenerOption(ClickListenerOption option, Set<PhotoWrapper> photos) {
        mClickListenerOption = option;
        mMultiSelectedPhotos = photos;
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
        PhotoWrapper photoWrapper = mPhotoWrappers.get(position);
        holder.mThumbnail.setOnClickListener(v -> {
            switch (mClickListenerOption) {
                case DEFAULT:
                    onClickDefault(photoWrapper, position);
                    break;
                case SHARE_ONE_ITEM:
                    onClickShareOneItem(photoWrapper);
                    break;
                case SHARE_MULTIPLE_ITEMS:
                    if (!mMultiSelectedPhotos.contains(photoWrapper)) {
                        onClickShareMultipleItems(photoWrapper);
                        mArray.put(position, true);
                        notifyDataSetChanged();

                    } else {
                        onClickShareMultipleItems(photoWrapper);
                        holder.mThumbnail.setColorFilter(null);
                        mArray.put(position, false);
                        notifyDataSetChanged();

                    }
                    break;
            }

        });
        holder.mThumbnail.setOnLongClickListener(v -> {
            setItemPosition(position);
            Log.d(PhotoAdapter.class.getSimpleName(), "Position " + position);
            return false;
        });

        Glide.with(mActivity).load(photoWrapper.getPath())
                .thumbnail(0.5f)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .transition(withCrossFade())
                .into(holder.mThumbnail);

        if (mArray.get(position)) {
            holder.mThumbnail.setColorFilter(mActivity.getResources()
                    .getColor(R.color.colorSelectedItem));
        } else {
            holder.mThumbnail.setColorFilter(mActivity.getResources().getColor(android.R.color.transparent));
        }

    }

    @Override
    public int getItemCount() {
        return mPhotoWrappers.size();
    }


    public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private final ImageView mThumbnail;
        private final SquareLayout mSquareLayout;


        PhotoViewHolder(View itemView) {
            super(itemView);
            mThumbnail = itemView.findViewById(R.id.photo_thumbnail);
            mSquareLayout = itemView.findViewById(R.id.sqaureLayout);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
            menu.add(0, v.getId(), 0, "Delete");
        }

    }

    private void setItemPosition(int position) {
        itemPosition = position;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    private void onClickDefault(PhotoWrapper photoWrapper, int position) {
        mMultiSelectedPhotos = null;
        List<File> photos = new ArrayList<>();
        SlideshowFragment slideshowFragment = SlideshowFragment.newInstance(false);
        for (PhotoWrapper photoWr : mPhotoWrappers) {
            photos.add(photoWr.getPath());
        }
        slideshowFragment.setSelectedPositionUnsortedList(position);
        slideshowFragment.setPhotos(photos);
        FragmentManager fm = mActivity.getSupportFragmentManager();
        fm.beginTransaction()
                .add(slideshowFragment, null)
                .commit();

    }

    private void onClickShareOneItem(PhotoWrapper photoWrapper) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(photoWrapper.getPath().toString());
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType(type);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoWrapper.getPath()));
        mClickListenerOption = ClickListenerOption.DEFAULT;
        mActivity.startActivity(intent);

    }

    private void onClickShareMultipleItems(PhotoWrapper photoWrapper) {
        if (!mMultiSelectedPhotos.contains(photoWrapper))
            mMultiSelectedPhotos.add(photoWrapper);
        else
            mMultiSelectedPhotos.remove(photoWrapper);
    }


}
