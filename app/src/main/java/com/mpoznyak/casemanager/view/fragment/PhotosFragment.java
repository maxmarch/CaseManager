package com.mpoznyak.casemanager.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.PhotoAdapter;
import com.mpoznyak.casemanager.presenter.CasePresenter;
import com.mpoznyak.casemanager.util.ClickListenerOption;
import com.mpoznyak.data.wrapper.PhotoWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PhotosFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PhotoAdapter mPhotosAdapter;
    private List<PhotoWrapper> mPhotoWrappers;
    private static final String KEY = "photos";
    private CasePresenter mCasePresenter;

    public static PhotosFragment newInstance(ArrayList<PhotoWrapper> photoWrappers) {
        PhotosFragment fragment = new PhotosFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY, photoWrappers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        mPhotoWrappers = getArguments().getParcelableArrayList(KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_gallery, parent, false);
        mRecyclerView = view.findViewById(R.id.photo_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mPhotosAdapter = new PhotoAdapter(mPhotoWrappers, new PhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v) {


            }
        }, getActivity());

        mRecyclerView.setAdapter(mPhotosAdapter);
        return view;

    }


    public void setClickListenerOption(ClickListenerOption option, Set<PhotoWrapper> photos) {
        mPhotosAdapter.setClickListenerOption(option, photos);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerForContextMenu(mRecyclerView);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete")) {
            mCasePresenter.deleteEntry(mPhotoWrappers.get(mPhotosAdapter.getItemPosition()));
            mPhotoWrappers.remove(mPhotosAdapter.getItemPosition());
            mPhotosAdapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public void setCasePresenter(CasePresenter presenter) {
        mCasePresenter = presenter;
    }
}
