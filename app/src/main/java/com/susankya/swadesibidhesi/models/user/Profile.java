package com.susankya.swadesibidhesi.models.user;

/**
 * Created by Ichha on 3/12/2018.
 */

public class Profile {
    private String pTitle,pSubtitle;
    private int pIcon;

    public Profile(String pTitle, String pSubtitle, int pIcon) {
        this.pTitle = pTitle;
        this.pSubtitle = pSubtitle;
        this.pIcon = pIcon;
    }

    public String getpTitle() {
        return pTitle;
    }

    public String getpSubtitle() {
        return pSubtitle;
    }

    public int getpIcon() {
        return pIcon;
    }
}
