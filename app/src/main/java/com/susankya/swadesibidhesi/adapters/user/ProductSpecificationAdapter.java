package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.models.WooCommerce.WcAttributes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.RecyclerViewHolder> {
    private List<WcAttributes> productList;
    private Context context;

    public ProductSpecificationAdapter(List<WcAttributes> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public ProductSpecificationAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_attribute_item, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductSpecificationAdapter.RecyclerViewHolder holder, final int position) {
        final WcAttributes attribute = productList.get(position);
        if (attribute.visible) {
            holder.title.setText(attribute.name+": ");
            if (!attribute.options.isEmpty()) holder.value.setText(attribute.options.get(0));
            else holder.value.setText("");
        } else {}
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.attributeTitle)
        TextView title;
        @BindView(R.id.attributeValue)
        TextView value;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
