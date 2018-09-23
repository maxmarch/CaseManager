package com.mpoznyak.document_assistant.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.interactor.MediaManagerInteractor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class SlideshowFragment extends DialogFragment {
    private String TAG = SlideshowFragment.class.getSimpleName();
    private List<File> mUnsortedFileList;
    private List<File> mPhotos;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    private Toolbar mToolbar;
    private ImageButton mSendButton;
    private int mSelectedPositionUnsortedList;
    private int mSelectedPositionPhotoList;
    private boolean mVisibleSendButton;
    private MediaManagerInteractor mMediaManagerInteractor;

    public void setInteractor(MediaManagerInteractor interactor) {
        mMediaManagerInteractor = interactor;
    }

    public SlideshowFragment() {

    }

    public static SlideshowFragment newInstance(boolean isVisibleSendBtn) {
        SlideshowFragment slideshowFragment = new SlideshowFragment();
        slideshowFragment.mVisibleSendButton = isVisibleSendBtn;
        return slideshowFragment;
    }


    public void setPhotos(List<File> photos) {
        mUnsortedFileList = photos;
        List<File> tempPhotoList = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            if (photos.get(i).toString().endsWith(".jpg")
                    || photos.get(i).toString().endsWith(".png")) {
                tempPhotoList.add(photos.get(i));
            }
        }
        mPhotos = tempPhotoList;
        for (int i = 0; i < mPhotos.size(); i++) {
            if (mUnsortedFileList.get(mSelectedPositionUnsortedList)
                    .getPath()
                    .equals(mPhotos.get(i).getPath())) {
                mSelectedPositionPhotoList = i;
            }
        }
    }

    public void setSelectedPositionUnsortedList(int position) {
        mSelectedPositionUnsortedList = position;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_slider, container, false);
        viewPager = v.findViewById(R.id.sliderViewPager);
        lblCount = v.findViewById(R.id.sliderCount);
        lblTitle = v.findViewById(R.id.sliderTitle);
        lblDate = v.findViewById(R.id.sliderDate);
        mToolbar = v.findViewById(R.id.toolbarPhotoSlider);
        mSendButton = v.findViewById(R.id.sendBtnPreviewPhoto);
        if (mVisibleSendButton) {
            mSendButton.setVisibility(View.VISIBLE);
        }
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        File photo = mPhotos.get(mSelectedPositionPhotoList);
        mSendButton.setOnClickListener(w -> {
            mMediaManagerInteractor.addPhoto(photo);
            Toast.makeText(getActivity(), getString(R.string.photo_was_added_successfully), Toast.LENGTH_SHORT).show();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .popBackStack();
        });
        setCurrentItem(mSelectedPositionPhotoList);

        return v;
    }


    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
    }

    private final ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            File photo = mPhotos.get(position);
            mSendButton.setOnClickListener(w -> {
                mMediaManagerInteractor.addPhoto(photo);
                Toast.makeText(getActivity(), getString(R.string.photo_was_added_successfully), Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .popBackStack();
            });
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) Objects.requireNonNull(getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = Objects.requireNonNull(layoutInflater).inflate(R.layout.photo_fullscreen_preview, container, false);

            ImageView photoPreview = v.findViewById(R.id.photoPreview);

            File photo = mPhotos.get(position);

            Glide.with(getActivity()).load(photo)
                    .thumbnail(0.5f)
                    .transition(withCrossFade())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .into(photoPreview);

            container.addView(v);

            return v;
        }

        @Override
        public int getCount() {
            return mPhotos.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
