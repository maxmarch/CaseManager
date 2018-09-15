package com.mpoznyak.casemanager.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.interactor.MediaManagerInteractor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_slider, container, false);
        viewPager = v.findViewById(R.id.sliderViewPager);
        lblCount = v.findViewById(R.id.sliderCount);
        lblTitle = v.findViewById(R.id.sliderTitle);
        lblDate = v.findViewById(R.id.sliderDate);
        mToolbar = v.findViewById(R.id.toolbarPhotoSlider);
        mSendButton = v.findViewById(R.id.sendBtnPreviewPhoto);
        if (mVisibleSendButton == true) {
            mSendButton.setVisibility(View.VISIBLE);
        }
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        File photo = mPhotos.get(mSelectedPositionPhotoList);
        mSendButton.setOnClickListener(w -> {
            mMediaManagerInteractor.addPhoto(photo);
            Log.d(SlideshowFragment.class.getSimpleName(), "Positiion " + photo.getName());
            getActivity().getSupportFragmentManager()
                    .popBackStack();
        });
        setCurrentItem(mSelectedPositionPhotoList);

        return v;
    }


    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            File photo = mPhotos.get(position);
            mSendButton.setOnClickListener(w -> {
                mMediaManagerInteractor.addPhoto(photo);
                Log.d(SlideshowFragment.class.getSimpleName(), "Positiion " + photo.getName());
                getActivity().getSupportFragmentManager()
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

    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(R.layout.photo_fullscreen_preview, container, false);

            ImageView photoPreview = (ImageView) v.findViewById(R.id.photoPreview);

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
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
