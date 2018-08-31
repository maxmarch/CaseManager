package com.mpoznyak.casemanager.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.data.model.Document;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {


    private List<Document> mEntries;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClick(View v);
    }

    public DocumentAdapter(List<Document> entries, OnItemClickListener listener) {
        mEntries = entries;
        mListener = listener;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_document_gallery, parent, false);
        return new DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, int position) {
        //TODO icon - parse name for extension and choose one of 3 icons: pdf, doc, txt, extend Entry Doc class
        holder.mDocumentName.setText(mEntries.get(position).getName());
        holder.mDocumentDate.setText(mEntries.get(position).getLastModified());
        holder.mDocumentSize.setText(String.valueOf(mEntries.get(position).getSize()));

    }

    @Override
    public int getItemCount() {
        return mEntries.size();
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

        }
    }
}
