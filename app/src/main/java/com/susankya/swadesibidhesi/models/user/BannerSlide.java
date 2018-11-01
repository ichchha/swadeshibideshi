package com.susankya.swadesibidhesi.models.user;

import java.util.List;

/**
 * Created by Ichha on 2/5/2018.
 */

public class BannerSlide implements HomeItem {
    List<Banner> bannerList;

    public BannerSlide(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public List<Banner> getBannerList() {
        return bannerList;
    }
}
