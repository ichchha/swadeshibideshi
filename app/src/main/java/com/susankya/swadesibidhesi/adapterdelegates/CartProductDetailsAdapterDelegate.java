package com.susankya.swadesibidhesi.adapterdelegates;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.joanzapata.iconify.widget.IconTextView;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.WishlistActivity;
import com.susankya.swadesibidhesi.models.user.BasketResultItem;
import com.susankya.swadesibidhesi.models.user.CartItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ichha on 2/4/2018.
 */

public class CartProductDetailsAdapterDelegate extends AdapterDelegate<List<CartItem>> {

    private LayoutInflater inflater;
    Activity activity;

    public CartProductDetailsAdapterDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
        this.activity = activity;
    }

    @Override
    protected boolean isForViewType(@NonNull List<CartItem> items, int position) {
        return items.get(position) instanceof BasketResultItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new CartProductViewHolder(inflater.inflate(R.layout.layout_cart_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<CartItem> items, int position,
                                 @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        final CartProductViewHolder vh = (CartProductViewHolder) holder;
        BasketResultItem productDetails = (BasketResultItem) items.get(position);
        final ArrayAdapter<String> spinnerArrayAdapter;
        final List<String> qtyList = new ArrayList<>();
        qtyList.add("1");
        qtyList.add("2");
        qtyList.add("3");
        qtyList.add("4");
        qtyList.add("5");
        qtyList.add("More");
        spinnerArrayAdapter = new ArrayAdapter<>(activity, R.layout.spinner_item, qtyList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
//        vh.spQty.setAdapter(spinnerArrayAdapter);
        vh.itemPrice.setText(productDetails.getPrice_excl_tax());
        vh.originalPrice.setText(productDetails.getPrice_incl_tax());
        vh.discount.setText(productDetails.getPrice_excl_tax_excl_discounts());
//        vh.spQty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                final String selectedItem = vh.spQty.getSelectedItem().toString();
//
//                if (selectedItem.equalsIgnoreCase("more")) {
//                    View dialogView = inflater.inflate(R.layout.quantity_select, null);
//                    final EditText quantity = dialogView.findViewById(R.id.editQty);
//                    android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(activity);
//                    dialogBuilder.setTitle("Enter the no of quantity you would like to order?")
//                            .setView(dialogView)
//                            .setPositiveButton("OK", null)
//                            .setNegativeButton("Cancel", null);
//                    final android.support.v7.app.AlertDialog dialog = dialogBuilder.create();
//                    dialog.show();
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            String Qty = quantity.getText().toString();
//                            dialog.dismiss();
//                            qtyList.add(Qty);
//                            spinnerArrayAdapter.notifyDataSetChanged();
//                            vh.spQty.setSelection(qtyList.size() - 1);
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        vh.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Delete the cart item")
                        .setMessage("Do you want to delete this item ?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(activity, "Cart item deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
        vh.btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WishlistActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    static class CartProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.txtSellerName)
        TextView sellerName;
        @BindView(R.id.itemPrice)
        TextView itemPrice;
        @BindView(R.id.originalPrice)
        TextView originalPrice;
        @BindView(R.id.discountOffer)
        TextView discount;
        @BindView(R.id.btnRemove)
        IconTextView btnRemove;
        @BindView(R.id.moveToWishlist)
        IconTextView btnWishlist;

        public CartProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
