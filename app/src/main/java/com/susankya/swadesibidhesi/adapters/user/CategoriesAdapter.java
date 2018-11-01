package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.HomeActivity;
import com.susankya.swadesibidhesi.fragments.user.ProductsFragment;
import com.susankya.swadesibidhesi.models.user.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.susankya.swadesibidhesi.Generic.FragmentKeys.CAT_ID;
import static com.susankya.swadesibidhesi.Generic.FragmentKeys.CAT_NAME;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.RecyclerViewHolder> {
    private List<Category> categoriesList = new ArrayList<>();
    public Context context;
    private OnItemClickListener listener;

    public CategoriesAdapter(List<Category> itemList, Context context, OnItemClickListener listener) {
        this.categoriesList = itemList;
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Category item);
    }

    @Override
    public CategoriesAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.categories_button, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoriesAdapter.RecyclerViewHolder holder, final int position) {
        holder.btnCategory.setText(categoriesList.get(position).getName());
        holder.bind(categoriesList.get(position), listener);
        final String category = categoriesList.get(position).getName();
        //categoriesList.get(position).getChildren().get(0).getId();
        final int cat_id =237;
        holder.btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt(CAT_ID, cat_id);
                bundle.putString(CAT_NAME, category);
                ProductsFragment fragment = new ProductsFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = ((HomeActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
//                transaction.addToBackStack("Home");
                transaction.commit();
                ((HomeActivity) context).performNoBackStackTransaction("Tag", fragment);
//
//                int cat_id = categoriesList.get(position).getId();
//                Intent intent = new Intent(context, LoginActivity.class);
//                intent.putExtra("CAT_ID",cat_id);
//                context.startActivity(intent);
//                final CatLevelTwo catLevelTwo= categoriesList.get(position).getChildren().get(1);
//                Toast.makeText(context, ""+catLevelTwo, Toast.LENGTH_SHORT).show();
//                List<String> children =new ArrayList<>();
//                int size = categoriesList.get(position).getChildren().size();
//                //Toast.makeText(context, ""+size2, Toast.LENGTH_SHORT).show();
//                for(int i=0;i<size;i++){
//                    children.add(categoriesList.get(position).getChildren().get(i).getName());
//                   // Toast.makeText(context, ""+categoriesList.get(position).getChildren().get(i).getId(), Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btnCategory)
        Button btnCategory;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Category item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
