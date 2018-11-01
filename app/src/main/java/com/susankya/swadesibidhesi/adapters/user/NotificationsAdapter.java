package com.susankya.swadesibidhesi.adapters.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.DraweeView;
import com.susankya.swadesibidhesi.R;
import com.susankya.swadesibidhesi.models.user.Notification;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ichha on 2/16/2018.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {
    private List<Notification> notificationList;
    private Context context;

    public NotificationsAdapter(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notification, null);
        return new NotificationViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        holder.nfTitle.setText(notificationList.get(position).getNotificationTitle());
        holder.nfDesc.setText(notificationList.get(position).getNotificationsDesc());
        holder.nfTime.setText(notificationList.get(position).getNotifiedTime());
        holder.nfImage.setImageResource(notificationList.get(position).getNfImage());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nfTitle)
        TextView nfTitle;
        @BindView(R.id.nfDesc)
        TextView nfDesc;
        @BindView(R.id.nfTime)
        TextView nfTime;
        @BindView(R.id.nfImage)
        DraweeView nfImage;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
