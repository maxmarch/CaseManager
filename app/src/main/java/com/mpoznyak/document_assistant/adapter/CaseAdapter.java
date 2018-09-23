package com.mpoznyak.document_assistant.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mpoznyak.document_assistant.R;
import com.mpoznyak.domain.model.Case;

import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.CaseViewHolder> {


    private final List<Case> mCases;
    private final OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View v);
    }

    public CaseAdapter(List<Case> cases, OnItemClickListener listener) {
        mListener = listener;
        mCases = cases;
    }

    public class CaseViewHolder extends RecyclerView.ViewHolder {
        public final ConstraintLayout viewForeground;
        final ConstraintLayout viewBackground;
        private final TextView mName;
        private final TextView mDate;

        CaseViewHolder(View view) {
            super(view);
            view.setOnClickListener(v -> mListener.onItemClick(v));
            viewForeground = view.findViewById(R.id.caseViewForeground);
            viewBackground = view.findViewById(R.id.caseViewBackground);
            mName = view.findViewById(R.id.mainCaseName);
            mDate = view.findViewById(R.id.mainCaseDate);
        }

    }

    @NonNull
    @Override
    public CaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_case_main, parent, false);
        return new CaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CaseViewHolder vh, int position) {
        Case aCase = mCases.get(position);
        vh.mName.setText(aCase.getName());
        vh.mDate.setText(aCase.getCreationDate());

    }

    @Override
    public int getItemCount() {
        return mCases.size();
    }

    public void removeItem(int position) {
        mCases.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Case item, int position) {
        mCases.add(position, item);
        notifyItemInserted(position);
    }

}
