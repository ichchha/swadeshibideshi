package com.susankya.swadesibidhesi.activities.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.susankya.swadesibidhesi.Generic.Utilities;
import com.susankya.swadesibidhesi.ItemDecorations.ItemOffsetDecoration;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.models.user.ProductItem;
import com.susankya.swadesibidhesi.models.user.WishLineItem;
import com.susankya.swadesibidhesi.models.user.Wishlist;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WishlistActivity extends AppCompatActivity {
    @BindView(R.id.wishlistRV)
    RecyclerView recyclerView;
    @BindView(R.id.progressBarLayout)
    View progressLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressTV)
    TextView progressTextView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.emtpyTextview)
    TextView emptyText;
    @BindView(R.id.emptyTextLayout)
    View emptyView;
    @BindView(R.id.empty_img)
    ImageView empty;
    private GridLayoutManager layoutManager;
    List<WishLineItem> wishLineItemList = new ArrayList<>();
    ProductItem productItem = new ProductItem();
    List<ProductItem> productItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wishlist);
        ButterKnife.bind(this);
        init();
        setUpRecyclerView();
        setUpToolbar();
    }

    private void setUpRecyclerView() {
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void init() {
        if (Utilities.isConnectionAvailable(this)) {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
//            if(Utilities.isLoggedIn(WishlistActivity.this))getWishList();
//            else{recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                empty.setImageDrawable(getResources().getDrawable(R.drawable.wishlist_bag));
                emptyText.setText("Please login to see your wishlist!");

        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_plug));
            emptyText.setText("OOPS, out of Connection");
        }
    }

    private void setUpToolbar() {
        toolbar.setTitle(R.string.fragment_wishlist);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarIcons));
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up

        return false;
    }

    private List<Wishlist> getWishlist(List<ProductItem> productItem) {
        List<Wishlist> list = new ArrayList<>();
        for (int i = 0; i < productItem.size(); i++) {
            list.add(new Wishlist(productItem.get(i)));
            //  list.add(new Wishlist(productItem.get(i).getTitle(),productItem.get(i).getPrice().getExcl_tax(),productItem.get(i).getImages().get(0).getOriginal(),productItem.get(i).getId()));
        }
        return list;
    }
}
