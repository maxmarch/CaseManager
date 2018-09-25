package com.mpoznyak.document_assistant.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mpoznyak.data.wrapper.PhotoWrapper;
import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.adapter.PhotoAdapter;
import com.mpoznyak.document_assistant.presenter.CasePresenter;
import com.mpoznyak.document_assistant.util.ClickListenerOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PhotoFragment extends Fragment {

    private static final String TAG = PhotoFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private PhotoAdapter mPhotosAdapter;
    private List<PhotoWrapper> mPhotoWrappers;
    private static final String KEY = "photos";
    private CasePresenter mCasePresenter;

    public static PhotoFragment newInstance(ArrayList<PhotoWrapper> photoWrappers) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY, photoWrappers);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        mPhotoWrappers = Objects.requireNonNull(getArguments()).getParcelableArrayList(KEY);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_gallery, parent, false);
        mRecyclerView = view.findViewById(R.id.photo_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mPhotosAdapter = new PhotoAdapter(mPhotoWrappers, v -> {


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
        setUserVisibleHint(true);
        registerForContextMenu(mRecyclerView);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterForContextMenu(mRecyclerView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setUserVisibleHint(false);
        unregisterForContextMenu(mRecyclerView);

    }

    public void unregisterForContextMenu() {
        unregisterForContextMenu(mRecyclerView);
    }

    public void registerForContextMenu() {
        registerForContextMenu(mRecyclerView);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete") && getUserVisibleHint()) {
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
