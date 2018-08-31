package com.mpoznyak.casemanager.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.view.fragment.DocumentGalleryFragment;
import com.mpoznyak.casemanager.view.fragment.PhotoGalleryFragment;
import com.mpoznyak.data.model.Document;
import com.mpoznyak.data.model.Photo;

import java.util.ArrayList;


public class CasePagerAdapter extends FragmentPagerAdapter {

    public static final int NUM = 2;
    private ArrayList<Document> mDocuments;
    private ArrayList<Photo> mPhotos;
    private Context mContext;

    public CasePagerAdapter(Context context, FragmentManager fm, ArrayList<Document> documents, ArrayList<Photo> photos) {
        super(fm);
        mDocuments = documents;
        mPhotos = photos;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DocumentGalleryFragment.newInstance(mDocuments);
            case 1:
                return PhotoGalleryFragment.newInstance(mPhotos);
            default:
                return null;
        }
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
