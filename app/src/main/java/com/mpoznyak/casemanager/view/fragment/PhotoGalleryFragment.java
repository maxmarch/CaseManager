package com.mpoznyak.casemanager.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.PhotoAdapter;
import com.mpoznyak.data.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PhotoAdapter mPhotoAdapter;
    private List<Photo> mPhotos;
    private static final String KEY = "photos";

    public static PhotoGalleryFragment newInstance(ArrayList<Photo> photos) {
        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY, photos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        mPhotos = getArguments().getParcelableArrayList(KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_gallery, parent, false);
        mRecyclerView = view.findViewById(R.id.photo_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //TODO init entries or path it from activity
        mPhotoAdapter = new PhotoAdapter(mPhotos, new PhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v) {

            }
        });
        mRecyclerView.setAdapter(mPhotoAdapter);
        return view;

    }
}
