package com.susankya.swadesibidhesi.notifications;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.view.MenuItem;

public class NotificationCountSetClass extends Activity {
    private static LayerDrawable icon;

    public NotificationCountSetClass() {
        //constructor
    }

    public static void setAddToCart(Context context, MenuItem item, int numMessages) {
        icon = (LayerDrawable) item.getIcon();
        SetNotificationCount.setBadgeCount(context, icon, numMessages);
    }

    public static void setAddToQuotation(Context context, MenuItem item, int numMessages) {
        icon = (LayerDrawable) item.getIcon();
        SetNotificationCount.setBadgeCount(context, icon, numMessages);

    }

    public static int setNotifyCount(int numMessages) {
        int count = numMessages;
        return count;
    }


}