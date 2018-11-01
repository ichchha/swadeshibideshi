package com.susankya.swadesibidhesi.models.WooCommerce;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.susankya.swadesibidhesi.models.user.ProductDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SCIT on 3/10/2017.
 */

public class WcProduct implements ProductDetails,Parcelable,Comparable{
    public int quantity;
    public String title;
    public int id;
    public String created_at;
    public String name;
    public String slug;
    public String permalink;
    public String type;
    public String status;
    public Boolean featured;
    public String catalog_visibility;
    public String description;
    public String short_description;
    public String price;
    public String regular_price;
    public String sale_price;
    public Boolean on_sale;
    public int total_sales;
    public String tax_status;
    public String tax_class;
    public Boolean manage_stock;
    public Object stock_quantity;
    public Boolean in_stock;
    public String weight;
    public Dimension dimensions;
    public Boolean shipping_required;
    public Boolean shipping_taxable;
    public String shipping_class;
    public Integer shipping_class_id;
    public Boolean reviews_allowed;
    public String average_rating;
    public Integer rating_count;
    public List<Integer> related_ids = null;
    public List<WcProduct> variations;
    public Integer parent_id;
    public String purchase_note;
    public List<String> categories = null;
    public List<WcProductImage> images =new ArrayList<>();
    public List<WcProductImage> image =new ArrayList<>();
    public List<WcAttributes> attributes = null;
    public List<Object> default_attributes = null;
    public List<Object> grouped_products = null;

    public WcProduct() {
    }

    protected WcProduct(Parcel in) {
        quantity = in.readInt();
        title = in.readString();
        id = in.readInt();
        name = in.readString();
        slug = in.readString();
        permalink = in.readString();
        type = in.readString();
        status = in.readString();
        byte tmpFeatured = in.readByte();
        featured = tmpFeatured == 0 ? null : tmpFeatured == 1;
        catalog_visibility = in.readString();
        description = in.readString();
        short_description = in.readString();
        price = in.readString();
        regular_price = in.readString();
        sale_price = in.readString();
        byte tmpOn_sale = in.readByte();
        on_sale = tmpOn_sale == 0 ? null : tmpOn_sale == 1;
        total_sales = in.readInt();
        tax_status = in.readString();
        tax_class = in.readString();
        byte tmpManage_stock = in.readByte();
        manage_stock = tmpManage_stock == 0 ? null : tmpManage_stock == 1;
        byte tmpIn_stock = in.readByte();
        in_stock = tmpIn_stock == 0 ? null : tmpIn_stock == 1;
        weight = in.readString();
        byte tmpShipping_required = in.readByte();
        shipping_required = tmpShipping_required == 0 ? null : tmpShipping_required == 1;
        byte tmpShipping_taxable = in.readByte();
        shipping_taxable = tmpShipping_taxable == 0 ? null : tmpShipping_taxable == 1;
        shipping_class = in.readString();
        if (in.readByte() == 0) {
            shipping_class_id = null;
        } else {
            shipping_class_id = in.readInt();
        }
        byte tmpReviews_allowed = in.readByte();
        reviews_allowed = tmpReviews_allowed == 0 ? null : tmpReviews_allowed == 1;
        average_rating = in.readString();
        if (in.readByte() == 0) {
            rating_count = null;
        } else {
            rating_count = in.readInt();
        }
        if (in.readByte() == 0) {
            parent_id = null;
        } else {
            parent_id = in.readInt();
        }
        purchase_note = in.readString();
        categories = in.createStringArrayList();
    }

    public static final Creator<WcProduct> CREATOR = new Creator<WcProduct>() {
        @Override
        public WcProduct createFromParcel(Parcel in) {
            return new WcProduct(in);
        }

        @Override
        public WcProduct[] newArray(int size) {
            return new WcProduct[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeString(title);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(slug);
        dest.writeString(permalink);
        dest.writeString(type);
        dest.writeString(status);
        dest.writeByte((byte) (featured == null ? 0 : featured ? 1 : 2));
        dest.writeString(catalog_visibility);
        dest.writeString(description);
        dest.writeString(short_description);
        dest.writeString(price);
        dest.writeString(regular_price);
        dest.writeString(sale_price);
        dest.writeByte((byte) (on_sale == null ? 0 : on_sale ? 1 : 2));
        dest.writeInt(total_sales);
        dest.writeString(tax_status);
        dest.writeString(tax_class);
        dest.writeByte((byte) (manage_stock == null ? 0 : manage_stock ? 1 : 2));
        dest.writeByte((byte) (in_stock == null ? 0 : in_stock ? 1 : 2));
        dest.writeString(weight);
        dest.writeByte((byte) (shipping_required == null ? 0 : shipping_required ? 1 : 2));
        dest.writeByte((byte) (shipping_taxable == null ? 0 : shipping_taxable ? 1 : 2));
        dest.writeString(shipping_class);
        if (shipping_class_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(shipping_class_id);
        }
        dest.writeByte((byte) (reviews_allowed == null ? 0 : reviews_allowed ? 1 : 2));
        dest.writeString(average_rating);
        if (rating_count == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rating_count);
        }
        if (parent_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(parent_id);
        }
        dest.writeString(purchase_note);
        dest.writeStringList(categories);
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return (((WcProduct) o).created_at).compareTo(this.created_at);
    }
}
