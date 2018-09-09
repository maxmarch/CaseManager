package com.mpoznyak.casemanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.data.wrapper.DocumentWrapper;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {


    private static final String TAG = DocumentAdapter.class.getSimpleName();
    private List<DocumentWrapper> mDocumentWrappers;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClick(View v);
    }

    public DocumentAdapter(List<DocumentWrapper> documentWrappers, OnItemClickListener listener) {
        mDocumentWrappers = documentWrappers;
        mListener = listener;
        Log.d(TAG, "Constructor calls, mDocumentWrappers: " + mDocumentWrappers);
    }

    public class DocumentViewHolder extends RecyclerView.ViewHolder {

        private ImageView mDocumentIcon;
        private TextView mDocumentName;
        private TextView mDocumentSize;
        private TextView mDocumentDate;

        public DocumentViewHolder(View itemView) {
            super(itemView);
            mDocumentDate = itemView.findViewById(R.id.dateDocument);
            mDocumentName = itemView.findViewById(R.id.nameDocument);
            mDocumentSize = itemView.findViewById(R.id.sizeDocument);
            mDocumentIcon = itemView.findViewById(R.id.iconDocument);
            Log.d(TAG, "create VH:" + mDocumentSize);

        }
    }

    @Override
    public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_document_caseview, parent, false);
        Log.d(TAG, "onCreateViewHolder: create");
        return new DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DocumentViewHolder holder, int position) {
        //TODO icon - parse name for extension and choose one of 3 icons: pdf, doc, txt, extend Entry Doc class
        DocumentWrapper documentWrapper = mDocumentWrappers.get(position);
        holder.mDocumentName.setText(documentWrapper.getName());
        holder.mDocumentDate.setText(documentWrapper.getLastModified());
        holder.mDocumentSize.setText(String.valueOf(documentWrapper.getSize()));
        Log.d(TAG, "onBindViewHolder: item name" + documentWrapper.getPath());

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount size: " + mDocumentWrappers.size());
        return mDocumentWrappers.size();

    }


}
