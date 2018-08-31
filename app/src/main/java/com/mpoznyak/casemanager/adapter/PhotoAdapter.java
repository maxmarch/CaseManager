package com.mpoznyak.casemanager.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.data.model.Photo;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<Photo> mPhotos;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClick(View v);
    }

    public PhotoAdapter(List<Photo> photos, OnItemClickListener listener) {
        mPhotos = photos;
        mListener = listener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_document_gallery, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        //TODO icon - parse name for extension and choose one of 3 icons: pdf, doc, txt, extend Entry Doc class
        //TODO implement photo bitmap from path
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }


    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView mPhoto;


        public PhotoViewHolder(View itemView) {
            super(itemView);
            mPhoto = itemView.findViewById(R.id.item_photo);

        }
    }
}
