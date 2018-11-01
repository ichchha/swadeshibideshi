package com.susankya.swadesibidhesi.adapters.WooCommerce;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.HomeActivity;
import com.susankya.swadesibidhesi.fragments.user.ProductsFragment;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductCategory;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.animation.Animation.RELATIVE_TO_SELF;
import static com.susankya.swadesibidhesi.Generic.FragmentKeys.CAT_NAME;
import static com.susankya.swadesibidhesi.Generic.FragmentKeys.CAT_SLUG;
import static com.susankya.swadesibidhesi.Generic.FragmentKeys.SUb_CAT_NAME;

public class NavCategoriesListAdapter extends ExpandableRecyclerViewAdapter<NavCategoriesListAdapter.CategoryViewHolder, NavCategoriesListAdapter.SubCategoryViewHolder> {
    Context context;
    List<WcProductCategory> categoryList ;

    public NavCategoriesListAdapter(List<? extends ExpandableGroup> groups, Context context,List<WcProductCategory> list) {
        super(groups);
//        categoryList = (List<WcProductCategory>) groups;
        categoryList = list;
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_level_one, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public SubCategoryViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_level_two, parent, false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(final SubCategoryViewHolder holder, int flatPosition, final ExpandableGroup group, final int childIndex) {
        final WcProductCategory item = (WcProductCategory) group.getItems().get(childIndex);
        String category = group.getTitle();
        holder.catLevel2Item.setText(Html.fromHtml(item.name));
//        Log.d("childID", item.id + item.count + "");
        holder.setListener(category, item);
    }

    @Override
    public void onBindGroupViewHolder(final CategoryViewHolder holder, final int flatPosition, final ExpandableGroup group) {
        holder.catLevel1Title.setText(Html.fromHtml(group.getTitle()));
        if (group.getItemCount() == 0) {
            holder.level1Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(SUb_CAT_NAME, group.getTitle());
                    bundle.putString(CAT_NAME, "" + Html.fromHtml(group.getTitle()));
                    bundle.putString(CAT_SLUG, categoryList.get(flatPosition).slug);
                    ProductsFragment fragment = new ProductsFragment();
                    fragment.setArguments(bundle);
                    ((HomeActivity) context).performNoBackStackTransaction("Tag", fragment);
                    ((HomeActivity) context).drawer.closeDrawer(GravityCompat.START);
                }
            });
        }
//        final WcProductCategory item = (WcProductCategory) group.getItems().get(flatPosition);
        Log.d("childID","slug"+categoryList.get(flatPosition).slug+"hey"+group.getTitle()+"count"+group.getItemCount()+"items"+group.getItems());

    }

    public class SubCategoryViewHolder extends ChildViewHolder {
        @BindView(R.id.txt_level_three)
        public TextView catLevel2Item;
        @BindView(R.id.product_listView)
        public ListView listView;
        @BindView(R.id.level2_layout)
        public LinearLayout level2Layout;

        public SubCategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setListener(final String category, final WcProductCategory item) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(SUb_CAT_NAME, item.name);
                    bundle.putString(CAT_NAME, "" + Html.fromHtml(category));
                    bundle.putString(CAT_SLUG, item.slug);
                    ProductsFragment fragment = new ProductsFragment();
                    fragment.setArguments(bundle);
                    ((HomeActivity) context).performNoBackStackTransaction("Tag", fragment);
                    ((HomeActivity) context).drawer.closeDrawer(GravityCompat.START);
                }
            });
        }
    }

    public class CategoryViewHolder extends GroupViewHolder {
        @BindView(R.id.down_arrow)
        public IconTextView arrow;
        @BindView(R.id.txt_level_one)
        public TextView catLevel1Title;
        @BindView(R.id.level1_layout)
        public RelativeLayout level1Layout;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // Animation Expand and Collapse
        @Override
        public void expand() {
            animateExpand();
        }

        @Override
        public void collapse() {
            animateCollapse();
        }

        public void animateExpand() {
            RotateAnimation rotate =
                    new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.setAnimation(rotate);
        }

        private void animateCollapse() {
            RotateAnimation rotate =
                    new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.setAnimation(rotate);
        }
    }
}
