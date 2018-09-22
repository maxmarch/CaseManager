package com.mpoznyak.casemanager.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.DocumentAdapter;
import com.mpoznyak.casemanager.presenter.CasePresenter;
import com.mpoznyak.casemanager.util.ClickListenerOption;
import com.mpoznyak.data.wrapper.DocumentWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DocumentsFragment extends Fragment {

    private static final String TAG = DocumentsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private DocumentAdapter mDocumentAdapter;
    private List<DocumentWrapper> mDocumentWrappers;
    private static final String KEY = "entries";
    private CasePresenter mCasePresenter;

    public static DocumentsFragment newInstance(ArrayList<DocumentWrapper> entries) {
        DocumentsFragment fragment = new DocumentsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY, entries);
        fragment.setArguments(args);
        Log.d(TAG, "newInstance");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDocumentWrappers = getArguments().getParcelableArrayList(KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_document_gallery, parent, false);
        mDocumentWrappers = getArguments().getParcelableArrayList(KEY);
        mRecyclerView = view.findViewById(R.id.documents_recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mDocumentAdapter = new DocumentAdapter(mDocumentWrappers, getActivity());
        Log.d(TAG, "DocumentAdapter recieves the next data: " + mDocumentWrappers);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mDocumentAdapter);


        return view;

    }

    public void unregisterForContextMenu() {
        unregisterForContextMenu(mRecyclerView);
    }

    public void registerForContextMenu() {
        registerForContextMenu(mRecyclerView);
    }


    @Override
    public void onResume() {
        super.onResume();
        mDocumentAdapter.notifyDataSetChanged();

        registerForContextMenu(mRecyclerView);

        Log.d(TAG, "onResume called");
    }


    public void setClickListenerOption(ClickListenerOption option, Set<DocumentWrapper> docs) {
        mDocumentAdapter.setClickListenerOption(option, docs);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        unregisterForContextMenu(mRecyclerView);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete")) {
            mCasePresenter.deleteEntry(mDocumentWrappers.get(mDocumentAdapter.getItemPosition()));
            mDocumentWrappers.remove(mDocumentAdapter.getItemPosition());
            mDocumentAdapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public void setCasePresenter(CasePresenter presenter) {
        mCasePresenter = presenter;
    }
}
