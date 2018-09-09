package com.mpoznyak.casemanager.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.DocumentAdapter;
import com.mpoznyak.data.wrapper.DocumentWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class DocumentsFragment extends Fragment implements Observer {

    private static final String TAG = DocumentsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private DocumentAdapter mDocumentAdapter;
    private List<DocumentWrapper> mDocumentWrappers;
    private static final String KEY = "entries";

    public static DocumentsFragment newInstance(ArrayList<DocumentWrapper> entries) {
        DocumentsFragment fragment = new DocumentsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY, entries);
        fragment.setArguments(args);
        Log.d(TAG, "newInstance method calles");
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
        mDocumentAdapter = new DocumentAdapter(mDocumentWrappers, new DocumentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v) {

            }
        });
        Log.d(TAG, "DocumentAdapter recieves the next data: " + mDocumentWrappers);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mDocumentAdapter);


        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        mDocumentAdapter.notifyDataSetChanged();
        Log.d(TAG, "onResume called");
    }

    @Override
    public void update(Observable o, Object args) {

    }
}
