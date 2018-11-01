package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.activities.user.OrdersActivity;
import com.susankya.swadesibidhesi.activities.user.UserProfileActivity;
import com.susankya.swadesibidhesi.activities.user.ViewProfileActivity;
import com.susankya.swadesibidhesi.activities.user.WishlistActivity;
import com.susankya.swadesibidhesi.models.user.Profile;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ichha on 3/12/2018.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private List<Profile> profileDetails;
    private Context context;

    public ProfileAdapter(List<Profile> profileDetails, Context context) {
        this.profileDetails = profileDetails;
        this.context = context;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_detail_item, null);
        return new ProfileViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        holder.nfTitle.setText(profileDetails.get(position).getpTitle());
        holder.nfDesc.setText(profileDetails.get(position).getpSubtitle());
        holder.nfImage.setImageResource(profileDetails.get(position).getpIcon());
    }

    @Override
    public int getItemCount() {
        return profileDetails.size();
    }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView nfTitle;
        @BindView(R.id.subtitle)
        TextView nfDesc;
        @BindView(R.id.icon)
        ImageView nfImage;

        public ProfileViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // context = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Intent intent;
                    switch (position){
                        case 0:
                            intent =  new Intent(v.getContext(), OrdersActivity.class);
                            break;

                        case 1:
                            intent =  new Intent(v.getContext(), WishlistActivity.class);
                            break;
                        case 2:
                            intent =  new Intent(v.getContext(), ViewProfileActivity.class);
                            break;

                        default:
                            intent =  new Intent(v.getContext(), UserProfileActivity.class);
                            break;
                    }
                    v.getContext().startActivity(intent);
                    ((UserProfileActivity)v.getContext()).finish();
                }
            });
        }
    }
}

