package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProductImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThumbViewAdapter extends RecyclerView.Adapter<ThumbViewAdapter.ThumbViewHolder> {
    private List<WcProductImage> imagesList;
    Context context;
    private final OnItemClickListener listener;

    public ThumbViewAdapter(List<WcProductImage> imagesList, Context context, OnItemClickListener listener) {
        this.imagesList = imagesList;
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(WcProductImage images, int position);
       // void onItemClick(Image images, int position, ThumbViewHolder holder);
    }

    @Override
    public ThumbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_thumb_image, null);
        return new ThumbViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ThumbViewHolder holder, int position) {
        Picasso.with(context).load(imagesList.get(position).src).into(holder.thumbImg);
       // holder.bind(imagesList.get(position), position, holder, listener);
        holder.bind(imagesList.get(position), position, listener);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ThumbViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbImg)
        public ImageView thumbImg;
        public boolean isSelected = false;

        public ThumbViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setBackground() {
            thumbImg.setSelected(true);
            thumbImg.setBackground(ContextCompat.getDrawable(context, R.drawable.thumb_pressed));
                notifyDataSetChanged();
        }

//        public void bind(final Image images, final int position, final ThumbViewHolder holder, final OnItemClickListener listener) {
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onItemClick(images, position, holder);
//                }
//            });
//        }
        public void bind(final WcProductImage images, final int position, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(images, position);
                }
            });
        }
    }
}

