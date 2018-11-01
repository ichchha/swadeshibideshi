package com.susankya.swadesibidhesi.models.user;

/**
 * Created by Ichha on 2/16/2018.
 */

public class Notification {
    private String notifiedTime,notificationTitle,notificationsDesc;
    private int nfImage;

    public int getNfImage() {
        return nfImage;
    }

    public String getNotifiedTime() {
        return notifiedTime;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getNotificationsDesc() {
        return notificationsDesc;
    }

    public Notification(String notifiedTime, String notificationTitle, String notificationsDesc) {
        this.notifiedTime = notifiedTime;
        this.notificationTitle = notificationTitle;
        this.notificationsDesc = notificationsDesc;
    }

    public Notification(String notifiedTime, String notificationTitle, String notificationsDesc, int nfImage) {
        this.notifiedTime = notifiedTime;
        this.notificationTitle = notificationTitle;
        this.notificationsDesc = notificationsDesc;
        this.nfImage = nfImage;
    }
}
