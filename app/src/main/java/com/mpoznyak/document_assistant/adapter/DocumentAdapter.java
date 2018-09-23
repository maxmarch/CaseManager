package com.mpoznyak.document_assistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpoznyak.data.wrapper.DocumentWrapper;
import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.util.ClickListenerOption;

import org.apache.commons.io.FilenameUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {


    private final SparseBooleanArray mArray = new SparseBooleanArray();
    private static final String TAG = DocumentAdapter.class.getSimpleName();
    private final List<DocumentWrapper> mDocumentWrappers;
    private final Context mContext;
    private ClickListenerOption mClickListenerOption;
    private static final String SEND = "send";
    private Set<DocumentWrapper> mSelectedDocumentsSet;
    private int itemPosition;

    public DocumentAdapter(List<DocumentWrapper> documentWrappers, Context context) {
        mDocumentWrappers = documentWrappers;
        mContext = context;
        mClickListenerOption = ClickListenerOption.DEFAULT;
        mSelectedDocumentsSet = new HashSet<>();
        Log.d(TAG, "Constructor calls, mDocumentWrappers: " + mDocumentWrappers);
    }

    public class DocumentViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private final ConstraintLayout mDocumentLayout;
        private final ImageView mDocumentIcon;
        private final TextView mDocumentName;
        private final TextView mDocumentSize;
        private final TextView mDocumentExtension;
        private final TextView mDocumentDate;

        DocumentViewHolder(View itemView) {
            super(itemView);
            mDocumentLayout = itemView.findViewById(R.id.layoutDocumentItem);
            mDocumentDate = itemView.findViewById(R.id.textDateDocumentItem);
            mDocumentExtension = itemView.findViewById(R.id.textDocumentExtensionItem);
            mDocumentName = itemView.findViewById(R.id.textNameDocumentItem);
            mDocumentSize = itemView.findViewById(R.id.textSizeDocumentItem);
            mDocumentIcon = itemView.findViewById(R.id.imageIconDocumentItem);
            mDocumentLayout.setBackgroundColor(mContext.getResources()
                    .getColor(R.color.colorBackground));

            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
            menu.add(0, v.getId(), 0, "Delete");
        }
    }

    private void setItemPosition(int position) {
        itemPosition = position;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_document_caseview, parent, false);
        Log.d(TAG, "onCreateViewHolder: create");
        return new DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, int position) {
        DocumentWrapper documentWrapper = mDocumentWrappers.get(position);
        String name = documentWrapper.getPath().getName();
        if (name.length() > 24) {
            name = name.substring(0, 24) + "...";
        }
        holder.mDocumentName.setText(name);
        holder.mDocumentDate.setText(documentWrapper.getLastModified());
        long size = documentWrapper.getPath().length();
        String sizeText = size / 1024 + " Kb";
        if (size > 600000) {
            if ((size / 1024 / 1024) < 1) {
                double sizeD = size / 1024 / 1024;
                sizeD = BigDecimal.valueOf(sizeD)
                        .setScale(2, BigDecimal.ROUND_HALF_UP)
                        .doubleValue();
                sizeText = sizeD + " Mb";
            } else {
                sizeText = size / 1024 / 1024 + " Mb";
            }
        }
        if (size > 600000000) {
            sizeText = size / 1024 / 1024 / 1024 + " Gb";
        }
        holder.mDocumentSize.setText(String.valueOf(sizeText));
        holder.mDocumentLayout.setOnClickListener(v -> {
            switch (mClickListenerOption) {
                case DEFAULT:
                    onClickDefault(documentWrapper);
                    break;
                case SHARE_ONE_ITEM:
                    onClickShareOneItem(documentWrapper);
                    break;
                case SHARE_MULTIPLE_ITEMS:
                    if (!mSelectedDocumentsSet.contains(documentWrapper)) {
                        onClickShareMultipleItems(documentWrapper);
                        mArray.put(position, true);
                        notifyDataSetChanged();

                    } else {
                        onClickShareMultipleItems(documentWrapper);
                        mArray.put(position, false);
                        notifyDataSetChanged();
                    }
                    break;
            }
        });
        holder.mDocumentLayout.setOnLongClickListener(v -> {
            setItemPosition(position);
            return false;
        });

        if (mArray.get(position)) {
            holder.mDocumentLayout.setBackgroundColor(mContext.getResources()
                    .getColor(R.color.colorSelectedItem));
        } else {
            holder.mDocumentLayout.setBackgroundColor(mContext.getResources()
                    .getColor(R.color.colorBackground));
        }

        String ext = FilenameUtils.getExtension(documentWrapper.getPath().toString());
        if (ext.equals("doc")) {
            holder.mDocumentExtension.setText("doc");
            holder.mDocumentIcon.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_blue_light));
        }
        if (ext.equals("docx")) {
            holder.mDocumentExtension.setText("docx");


            holder.mDocumentIcon.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_blue_light));

        }
        if (ext.equals("pdf")) {
            holder.mDocumentExtension.setText("pdf");

            holder.mDocumentIcon.setBackgroundColor(mContext.getResources().getColor(android.R.color.holo_red_dark));

        }






    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount size: " + mDocumentWrappers.size());
        return mDocumentWrappers.size();

    }

    public void setClickListenerOption(ClickListenerOption option, Set<DocumentWrapper> docs) {
        mClickListenerOption = option;
        mSelectedDocumentsSet = docs;
    }

    private void onClickDefault(DocumentWrapper documentWrapper) {
        mSelectedDocumentsSet = null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(documentWrapper.getPath().toString());
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        intent.setDataAndType(Uri.fromFile(documentWrapper.getPath()), type);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mContext.startActivity(intent);
    }

    private void onClickShareOneItem(DocumentWrapper documentWrapper) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(documentWrapper.getPath().toString());
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType(type);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(documentWrapper.getPath()));
        mClickListenerOption = ClickListenerOption.DEFAULT;
        mContext.startActivity(intent);

    }

    private void onClickShareMultipleItems(DocumentWrapper documentWrapper) {
        if (!mSelectedDocumentsSet.contains(documentWrapper))
            mSelectedDocumentsSet.add(documentWrapper);
        else
            mSelectedDocumentsSet.remove(documentWrapper);
    }


}
