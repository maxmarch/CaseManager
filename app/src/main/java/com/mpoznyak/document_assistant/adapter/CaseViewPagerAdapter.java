package com.mpoznyak.document_assistant.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mpoznyak.data.wrapper.DocumentWrapper;
import com.mpoznyak.data.wrapper.PhotoWrapper;
import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.presenter.CasePresenter;
import com.mpoznyak.document_assistant.util.ClickListenerOption;
import com.mpoznyak.document_assistant.view.fragment.DocumentFragment;
import com.mpoznyak.document_assistant.view.fragment.PhotoFragment;

import java.util.ArrayList;
import java.util.Set;


public class CaseViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = CaseViewPagerAdapter.class.getSimpleName();
    private static final int NUM = 2;
    private final ArrayList<DocumentWrapper> mDocumentWrappers;
    private final ArrayList<PhotoWrapper> mPhotoWrappers;
    private final Context mContext;
    private final DocumentFragment mDocumentFragment;
    private final PhotoFragment mPhotoFragment;
    private CasePresenter mCasePresenter;


    public CaseViewPagerAdapter(Context context, FragmentManager fm, ArrayList<DocumentWrapper> documentWrappers, ArrayList<PhotoWrapper> photoWrappers) {
        super(fm);
        mDocumentWrappers = documentWrappers;
        mPhotoWrappers = photoWrappers;
        mContext = context;
        mDocumentFragment = DocumentFragment.newInstance(mDocumentWrappers);
        mPhotoFragment = PhotoFragment.newInstance(mPhotoWrappers);


    }

    public void setSingleClickOption(ClickListenerOption option) {
        mDocumentFragment.setClickListenerOption(option, null);
        mPhotoFragment.setClickListenerOption(option, null);
    }

    public void setMultiClickOption(ClickListenerOption option, Set<DocumentWrapper> docs
            , Set<PhotoWrapper> photos) {
        mDocumentFragment.setClickListenerOption(option, docs);
        mPhotoFragment.setClickListenerOption(option, photos);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mDocumentFragment;
            case 1:
                return mPhotoFragment;
            default:
                return null;
        }
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
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
        mDocumentFragment.setCasePresenter(mCasePresenter);
        mPhotoFragment.setCasePresenter(mCasePresenter);
    }

    public void unregisterForContextMenuPhotoFragment() {
        mPhotoFragment.unregisterForContextMenu();
    }

    public void registerForContextMenuPhotoFragment() {
        mPhotoFragment.registerForContextMenu();
    }

    public void unregisterForContextMenuDocumentFragment() {
        mDocumentFragment.unregisterForContextMenu();
    }

    public void registerForContextMenuDocumentFragment() {
        mDocumentFragment.registerForContextMenu();
    }

}
