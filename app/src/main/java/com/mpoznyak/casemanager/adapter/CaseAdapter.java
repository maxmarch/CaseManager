package com.mpoznyak.casemanager.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.domain.model.Case;

import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.CaseViewHolder> {


    private List<Case> mCases;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View v);
    }

    public CaseAdapter(List<Case> cases, OnItemClickListener listener) {
        mListener = listener;
        mCases = cases;
    }

    public class CaseViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mName, mDate;

        public CaseViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v);

                }
            });
            mCardView = (CardView) view.findViewById(R.id.mainCasesCard);
            mName = (TextView) view.findViewById(R.id.mainCaseName);
            mDate = (TextView) view.findViewById(R.id.mainCaseDate);
        }

    }

    @Override
    public CaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_case, parent, false);
        return new CaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CaseViewHolder vh, int position) {
        Case aCase = mCases.get(position);
        vh.mName.setText(aCase.getName());
        vh.mDate.setText(aCase.getCreationDate());

    }

    @Override
    public int getItemCount() {
        return mCases.size();
    }


}
