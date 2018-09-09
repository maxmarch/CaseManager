package com.mpoznyak.casemanager.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.util.FragmentObserver;
import com.mpoznyak.casemanager.view.fragment.DocumentsFragment;
import com.mpoznyak.casemanager.view.fragment.PhotosFragment;
import com.mpoznyak.data.wrapper.DocumentWrapper;
import com.mpoznyak.data.wrapper.PhotoWrapper;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class CaseViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = CaseViewPagerAdapter.class.getSimpleName();
    private Observable mObservers = new FragmentObserver();
    public static final int NUM = 2;
    private ArrayList<DocumentWrapper> mDocumentWrappers;
    private ArrayList<PhotoWrapper> mPhotoWrappers;
    private Context mContext;

    public CaseViewPagerAdapter(Context context, FragmentManager fm, ArrayList<DocumentWrapper> documentWrappers, ArrayList<PhotoWrapper> photoWrappers) {
        super(fm);
        mDocumentWrappers = documentWrappers;
        mPhotoWrappers = photoWrappers;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        mObservers.deleteObservers();
        switch (position) {
            case 0:
                Fragment fragment0 = DocumentsFragment.newInstance(mDocumentWrappers);
                Log.d(TAG, "getItem calls " + fragment0);
                mObservers.addObserver((Observer) fragment0);
                return fragment0;
            case 1:
                Fragment fragment1 = PhotosFragment.newInstance(mPhotoWrappers);
                mObservers.addObserver((Observer) fragment1);
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
}
