package com.mpoznyak.casemanager.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.DocumentAdapter;
import com.mpoznyak.data.model.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentGalleryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DocumentAdapter mDocumentAdapter;
    private List<Document> mDocuments;
    private static final String KEY = "entries";

    public static DocumentGalleryFragment newInstance(ArrayList<Document> entries) {
        DocumentGalleryFragment fragment = new DocumentGalleryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY, entries);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDocuments = getArguments().getParcelableArrayList(KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_document_gallery, parent, false);
        mRecyclerView = view.findViewById(R.id.documents_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDocumentAdapter = new DocumentAdapter(mDocuments, new DocumentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v) {

            }
        });
        mRecyclerView.setAdapter(mDocumentAdapter);
        return view;

    }


}
