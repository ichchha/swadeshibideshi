package com.susankya.swadesibidhesi.adapters.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joanzapata.iconify.widget.IconTextView;
import com.susankya.swadesibidhesi.Generic.Keys;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;
import com.susankya.swadesibidhesi.models.user.Size;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartProductListAdapter extends RecyclerView.Adapter<CartProductListAdapter.RecyclerViewHolder> {
    private ArrayList<WcProduct> cartItemsList;
    public Context context;
    private boolean fromQuote;
    private final OnItemClickListener listener;
    private QtyListAdapter qtyListAdapter;
    ArrayList<Integer> productIDListOfCartItems = new ArrayList<>();

    public CartProductListAdapter(ArrayList<WcProduct> itemList, Boolean fromQuote, Context context, OnItemClickListener listener) {
        this.cartItemsList = itemList;
        this.context = context;
        this.listener = listener;
        this.fromQuote = fromQuote;
    }

    public interface OnItemClickListener {
        void onItemClick(WcProduct item);
    }

    @Override
    public CartProductListAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cart_items, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final CartProductListAdapter.RecyclerViewHolder holder, final int position) {
        final WcProduct wcProduct = cartItemsList.get(position);
        holder.itemName.setText(wcProduct.title);
        holder.productPrice.setText(wcProduct.price);
        holder.priceLayout.setVisibility(View.VISIBLE);
        float quantity = wcProduct.quantity;
        Log.d("quantity", "" + wcProduct.images.get(0).src);
        if (!wcProduct.images.isEmpty()) holder.itemImage.setImageURI(wcProduct.images.get(0).src);
        else {
        }
        holder.itemQty.setText(String.valueOf(wcProduct.quantity));  //while adding to cart only 1 quantity is added at first!
        qtyListAdapter = new QtyListAdapter(context, getQuantityOption());
        holder.qtyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setAdapter(qtyListAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                wcProduct.quantity = which + 1;
                                App.db().putListCartItems(Keys.PRODUCT_LIST_IN_WC_BASKET, cartItemsList);
                                notifyItemChanged(position, wcProduct);
                            }
                        })
                        .setTitle("Change Quantity")
                        .setCancelable(true)
                        .show();
            }
        });


        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Cart Item")
                        .setMessage("Are you sure you want to delete ?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cartItemsList.remove(wcProduct);
                                notifyDataSetChanged();
                                //removing products list from tinydb
                                if (fromQuote) removeFromQuotation(wcProduct);
                                else removeFromCart(wcProduct);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    private void removeFromCart(WcProduct wcProduct) {
        ArrayList<WcProduct> cartProductList = App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET);
        for (WcProduct product : cartProductList) {
            if (product.id == wcProduct.id) cartProductList.remove(product);
            App.db().putListCartItems(Keys.PRODUCT_LIST_IN_WC_BASKET, cartProductList);
        }

        Toast.makeText(context, "Item removed from cart successfully!", Toast.LENGTH_SHORT).show();
        ArrayList<Integer> cartProductIDList = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_BASKET);
        for (Integer productId : cartProductIDList) {
            if (productId == wcProduct.id)
                cartProductIDList.remove(new Integer(productId));
            App.db().putListInt(Keys.PRODUCT_ID_LIST_IN_BASKET, cartProductIDList);
        }
    }

    private void removeFromQuotation(WcProduct wcProduct) {
        ArrayList<WcProduct> cartProductList = App.db().getListCartItem(Keys.PRODUCT_LIST_IN_Quotation);
        for (WcProduct product : cartProductList) {
            if (product.id == wcProduct.id) cartProductList.remove(product);
            App.db().putListCartItems(Keys.PRODUCT_LIST_IN_Quotation, cartProductList);
        }

        Toast.makeText(context, "Item removed from cart successfully!", Toast.LENGTH_SHORT).show();
        ArrayList<Integer> cartProductIDList = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_Quotation);
        for (Integer productId : cartProductIDList) {
            if (productId == wcProduct.id)
                cartProductIDList.remove(new Integer(productId));
            App.db().putListInt(Keys.PRODUCT_ID_LIST_IN_Quotation, cartProductIDList);
        }
    }

    private ArrayList<Size> getQuantityOption() {
        ArrayList<Size> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) list.add(new Size(String.valueOf(i)));
        return list;
    }

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.priceLayout)
        RelativeLayout priceLayout;
        @BindView(R.id.productName)
        TextView itemName;
        @BindView(R.id.itemPrice)
        TextView productPrice;
        @BindView(R.id.itemQty)
        TextView itemQty;
        @BindView(R.id.qtyLayout)
        LinearLayout qtyLayout;
        @BindView(R.id.imgProduct)
        SimpleDraweeView itemImage;
        @BindView(R.id.btnRemove)
        IconTextView btnRemove;
        @BindView(R.id.moveToWishlist)
        IconTextView moveToWishlist;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final WcProduct item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
