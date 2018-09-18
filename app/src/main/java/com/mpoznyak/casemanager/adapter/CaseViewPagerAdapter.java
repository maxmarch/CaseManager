package com.mpoznyak.casemanager.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.presenter.CasePresenter;
import com.mpoznyak.casemanager.util.ClickListenerOption;
import com.mpoznyak.casemanager.view.fragment.DocumentsFragment;
import com.mpoznyak.casemanager.view.fragment.PhotosFragment;
import com.mpoznyak.data.wrapper.DocumentWrapper;
import com.mpoznyak.data.wrapper.PhotoWrapper;

import java.util.ArrayList;
import java.util.Set;


public class CaseViewPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

    private static final String TAG = CaseViewPagerAdapter.class.getSimpleName();
    public static final int NUM = 2;
    private ArrayList<DocumentWrapper> mDocumentWrappers;
    private ArrayList<PhotoWrapper> mPhotoWrappers;
    private Context mContext;
    public DocumentsFragment fragment0;
    private PhotosFragment fragment1;
    private CasePresenter mCasePresenter;


    public CaseViewPagerAdapter(Context context, FragmentManager fm, ArrayList<DocumentWrapper> documentWrappers, ArrayList<PhotoWrapper> photoWrappers) {
        super(fm);
        mDocumentWrappers = documentWrappers;
        mPhotoWrappers = photoWrappers;
        mContext = context;
        fragment0 = DocumentsFragment.newInstance(mDocumentWrappers);
        fragment1 = PhotosFragment.newInstance(mPhotoWrappers);


    }

    public void setSingleClickOption(ClickListenerOption option) {
        fragment0.setClickListenerOption(option, null);
        fragment1.setClickListenerOption(option, null);
    }

    public void setMultiClickOption(ClickListenerOption option, Set<DocumentWrapper> docs
            , Set<PhotoWrapper> photos) {
        fragment0.setClickListenerOption(option, docs);
        fragment1.setClickListenerOption(option, photos);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragment0;
            case 1:
                return fragment1;
            default:
                return null;
        }
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return NUM;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getResources().getString(R.string.page1);
            case 1:
                return mContext.getResources().getString(R.string.page2);
            default:
                return null;
        }
    }

    public void setCasePresenter(CasePresenter casePresenter) {
        mCasePresenter = casePresenter;
        fragment0.setCasePresenter(mCasePresenter);
        fragment1.setCasePresenter(mCasePresenter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            fragment1.unregisterForContextMenu();
            fragment0.registerForContextMenu();
        } else {
            fragment0.unregisterForContextMenu();
            fragment1.registerForContextMenu();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
