package com.susankya.swadesibidhesi.adapterdelegates;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.models.user.ProductDetails;
import com.susankya.swadesibidhesi.models.user.Size;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemSizeAdapterDelegate extends AdapterDelegate<List<ProductDetails>> {

    private LayoutInflater inflater;
    Activity activity;

    public ItemSizeAdapterDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
        this.activity = activity;
    }

    @Override
    protected boolean isForViewType(@NonNull List<ProductDetails> items, int position) {
        return items.get(position) instanceof Size;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new SizeViewHolder(inflater.inflate(R.layout.layout_item_size, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<ProductDetails> items, int position,
                                 @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        SizeViewHolder vh = (SizeViewHolder) holder;
        Size itemSize = (Size) items.get(position);
       // vh.itemSizeLayout.removeAllViews();
        vh.itemSize.setText(itemSize.getSize());
       // vh.itemSize.setBackground(getResources().getDrawable(R.drawable.border_grey));
       // vh.itemSizeLayout.addView(vh.itemSize);
    }
    static class SizeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemSizeLayout)
        FlowLayout itemSizeLayout;
        @BindView(R.id.sizeLayout)
        LinearLayout sizeLayout;
        @BindView(R.id.txtSize)
        TextView itemSize;

        public SizeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
