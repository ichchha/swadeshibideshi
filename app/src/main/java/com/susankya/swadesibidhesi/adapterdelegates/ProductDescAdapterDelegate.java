package com.susankya.swadesibidhesi.adapterdelegates;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.joanzapata.iconify.widget.IconTextView;
import com.susankya.swadesibidhesi.ItemDecorations.HorizontalSpaceItemDecoration;
import com.susankya.swadesibidhesi.ItemDecorations.VerticalSpaceItemDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.adapters.user.ProductSpecificationAdapter;
import com.susankya.swadesibidhesi.adapters.user.ProductVariationAdapter;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.user.ProductDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDescAdapterDelegate extends AdapterDelegate<List<ProductDetails>> {

    private LayoutInflater inflater;
    Context activity;
    Boolean addedToWishlist = false;
    private String currency = "Rs. ";

    public ProductDescAdapterDelegate(Context activity) {
        inflater = LayoutInflater.from(activity);
        this.activity = activity;
    }

    @Override
    protected boolean isForViewType(@NonNull List<ProductDetails> items, int position) {
        return items.get(position) instanceof WcProduct;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ProductDescSliderViewHolder(inflater.inflate(R.layout.layout_product_description, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<ProductDetails> items, int position,
                                 @NonNull final RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        final ProductDescSliderViewHolder vh = (ProductDescSliderViewHolder) holder;
        final WcProduct productDesc = (WcProduct) items.get(position);

        checkProductInfo(productDesc, vh.info);

        if (!productDesc.attributes.isEmpty() && productDesc.variations.isEmpty()) {
            vh.attributesLayout.setVisibility(View.VISIBLE);
            vh.attributeName.setText("Specifications:");
            vh.attributeRV.setLayoutManager(new LinearLayoutManager(activity));
            vh.attributeRV.addItemDecoration(new VerticalSpaceItemDecoration(8));
            vh.attributeRV.setAdapter(new ProductSpecificationAdapter(productDesc.attributes, activity));
        } else if (!productDesc.variations.isEmpty()) {
            vh.attributesLayout.setVisibility(View.VISIBLE);
            vh.attributeName.setText("Select " + productDesc.attributes.get(0).name + " :");
            vh.attributeRV.setLayoutManager(new GridLayoutManager(activity, 3));
            vh.attributeRV.addItemDecoration(new HorizontalSpaceItemDecoration(8));
            vh.attributeRV.setAdapter(new ProductVariationAdapter(productDesc.title, productDesc.variations, activity, vh.productPrice, vh.dimensionLayout, vh.txtDimension));
        } else {
        }

        vh.productName.setText(productDesc.title);
        if(productDesc.price=="") vh.productPrice.setVisibility(View.GONE);
        else vh.productPrice.setText(currency+productDesc.price);
        String desc = productDesc.description;
        String d = String.valueOf(Html.fromHtml(desc));
        if (d.contains("vc_column_text")) {
            String[] s = d.split("vc_column_text");
            String a = s[1].replaceAll("]", "");
            String b = a.replaceAll("[/]", "");
            vh.description.setText(b);
        } else {
            vh.description.setText(d);
        }

        if (productDesc.on_sale && productDesc.price != "") {
            ((ProductDescSliderViewHolder) holder).discountLayout.setVisibility(View.VISIBLE);
            ((ProductDescSliderViewHolder) holder).originalPriceLayout.setVisibility(View.VISIBLE);
            ((ProductDescSliderViewHolder) holder).originalPrice.setText(currency + productDesc.regular_price);
            ((ProductDescSliderViewHolder) holder).originalPrice.setPaintFlags(((ProductDescSliderViewHolder) holder).originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            String salePrice = productDesc.sale_price;
            String regularPrice = productDesc.regular_price;
            Log.d("test", salePrice + "rp" + regularPrice);
            if (productDesc.regular_price == "" || productDesc.sale_price == null) {
                ((ProductDescSliderViewHolder) holder).discount.setText(productDesc.sale_price);
            } else {
                float rp = Float.valueOf(regularPrice);
                float sp = Float.valueOf(salePrice);
                float sub = rp - sp;
                float discountPercentage = (sub / rp) * 100;
                String discount = String.format("%,.0f", discountPercentage);
                ((ProductDescSliderViewHolder) holder).discount.setText("( " + discount + "% OFF" + " )");
            }

        } else {
            ((ProductDescSliderViewHolder) holder).discountLayout.setVisibility(View.GONE);
            ((ProductDescSliderViewHolder) holder).originalPriceLayout.setVisibility(View.GONE);
        }

        if (productDesc.in_stock) vh.availability.setText(R.string.available);
        else {
            vh.availability.setText(R.string.out_of_stock);
            vh.availability.setTextColor(activity.getResources().getColor(R.color.colorRed));
        }
    }

    private void checkProductInfo(final WcProduct productDesc, TextView info) {
        if (productDesc.short_description == "") {
        } else {
            info.setVisibility(View.VISIBLE);
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MaterialDialog materialDialog = new MaterialDialog.Builder(activity)
                            .title("Information")
                            .customView(R.layout.layout_short_description, true)
                            .canceledOnTouchOutside(true)
                            .show();
                    TextView txtInfo = materialDialog.getCustomView().findViewById(R.id.txtInfo);
                    txtInfo.setText(Html.fromHtml(productDesc.short_description));
                }
            });
        }
    }

    static class ProductDescSliderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDimension)
        TextView txtDimension;
        @BindView(R.id.dimensionLayout)
        LinearLayout dimensionLayout;
        @BindView(R.id.txtProductName)
        TextView productName;
        @BindView(R.id.priceLayout)
        RelativeLayout priceLayout;
        @BindView(R.id.itemPrice)
        TextView productPrice;
        @BindView(R.id.originalPrice)
        TextView originalPrice;
        @BindView(R.id.discountOffer)
        TextView discount;
        @BindView(R.id.txtDescription)
        TextView description;
        @BindView(R.id.txtRatings)
        TextView ratings;
        @BindView(R.id.txtAvailability)
        TextView availability;
        @BindView(R.id.txtShare)
        IconTextView btnShare;
        @BindView(R.id.txtWishlist)
        IconTextView btnWishlist;
        @BindView(R.id.attributesLayout)
        LinearLayout attributesLayout;
        @BindView(R.id.discountLayout)
        RelativeLayout discountLayout;
        @BindView(R.id.originalPriceLayout)
        RelativeLayout originalPriceLayout;
        @BindView(R.id.attributeName)
        TextView attributeName;
        @BindView(R.id.info)
        TextView info;
        @BindView(R.id.attributeRV)
        RecyclerView attributeRV;

        public ProductDescSliderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
