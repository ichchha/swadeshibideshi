package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.ProductDetailsActivity;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.user.CategoryItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.susankya.swadesibidhesi.activities.user.ProductDetailsActivity.wcProduct;

public class ProductVariationAdapter extends RecyclerView.Adapter<ProductVariationAdapter.RecyclerViewHolder> {
    private List<WcProduct> productList;
    private Context context;
    private TextView productPrice, dimension;
    private LinearLayout dimensionLayout;
    private int selected_position;
    private String length, width, height,unit,title;

    public ProductVariationAdapter(String title, List<WcProduct> productList, Context context, TextView productPrice, LinearLayout dimensionLayout, TextView txtDimension) {
        this.productList = productList;
        this.context = context;
        this.productPrice = productPrice;
        this.dimension = txtDimension;
        this.dimensionLayout = dimensionLayout;
        this.title =title;
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryItem item, int position);
    }

    @Override
    public ProductVariationAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_button, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductVariationAdapter.RecyclerViewHolder holder, final int position) {
        final WcProduct product = productList.get(position);
        length = product.dimensions.length;
        width = product.dimensions.width;
        height = product.dimensions.height;
        unit = product.dimensions.unit;
        holder.btnSize.setText(product.attributes.get(0).option);

        if (position == selected_position) {

            ProductDetailsActivity.productID = product.id;
            wcProduct.id=product.id;
            wcProduct.price=product.price;
            wcProduct.title=title+" - "+product.attributes.get(0).option;
            if (length == "" && width == "" && height == "") dimensionLayout.setVisibility(View.GONE);
            else dimensionLayout.setVisibility(View.VISIBLE);
            productPrice.setText(product.price);
            dimension.setText(" "+length+" X "+height+" "+unit);
            holder.btnSize.setSelected(true);
            holder.btnSize.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            holder.btnSize.setBackground(context.getResources().getDrawable(R.drawable.size_button_pressed));
        } else {
//            wcProduct.title=title;
            holder.btnSize.setBackground(context.getResources().getDrawable(R.drawable.size_button));
            holder.btnSize.setTextColor(context.getResources().getColor(R.color.secondaryText));
            holder.btnSize.setSelected(false);
        }
        holder.btnSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wcProduct.title=title+" - "+product.attributes.get(0).option;
                selected_position = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

//        Log.d("check", "onBindViewHolder: outside"+product.id+product.price);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btnSize)
        TextView btnSize;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final CategoryItem item, final int position, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item, position);
                }
            });
        }
    }
}
