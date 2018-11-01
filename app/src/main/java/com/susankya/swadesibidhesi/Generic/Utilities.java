package com.susankya.swadesibidhesi.Generic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.susankya.swadesibidhesi.application.App;
import com.susankya.swadesibidhesi.models.WooCommerce.WcBilling;
import com.susankya.swadesibidhesi.models.WooCommerce.WcCategory;
import com.susankya.swadesibidhesi.models.WooCommerce.WcOrderItem;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by Ichha on 3/19/2018.
 */

public class Utilities {
    public static int wishlistCountCart = 0;

    public static boolean isConnectionAvailable(Activity activity) {
        ConnectivityManager connMgr = (ConnectivityManager)
                activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) return true;
        else return false;
    }

    public static boolean isLoggedIn(Activity activity) {
        if (App.db().getBoolean(Keys.USER_LOGGED_IN)) return true;
        else return false;
    }

    public static void addProductToCart(Activity activity, int productId) {
        ArrayList<Integer> list = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_BASKET);
        list.add(productId);
        App.db().putListInt(Keys.PRODUCT_ID_LIST_IN_BASKET, list);
    }

    public static void addWcBillingAddress(Activity activity, WcBilling billing) {
        List<WcBilling> list = App.db().getBillingAddressList(FragmentKeys.WC_BILLING_ADDRESS_LIST);
        list.add(billing);
        App.db().putListBillingAddresses(FragmentKeys.WC_BILLING_ADDRESS_LIST, list);
    }

    public static void addToOrdersList(WcOrderItem orderItem) {
        List<WcOrderItem> list = App.db().getOrderLists(Keys.PRODUCT_LIST_IN_WC_ORDERS);
        list.add(orderItem);
        App.db().putListOrders(Keys.PRODUCT_LIST_IN_WC_ORDERS, list);
    }

    public static void addProductToWcCart(Activity activity, int productId, WcProduct product) {
        Log.d("product", "addProductToWcCart: reached here");
        //to store product ids so that products already added to cart can be shown as "Already added to cart"
        ArrayList<Integer> productIdList = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_BASKET);
        productIdList.add(productId);
        App.db().putListInt(Keys.PRODUCT_ID_LIST_IN_BASKET, productIdList);

        //to store product items so that its easy to show them directly in cart items listing activity
        ArrayList<WcProduct> productList = App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET);
        productList.add(product);
        App.db().putListCartItems(Keys.PRODUCT_LIST_IN_WC_BASKET, productList);
    }

    public static void addProductToQuotation(Activity activity, int productId, WcProduct product) {
        Log.d("product", "addProductToWcCart: reached here");
        //to store product ids so that products already added to cart can be shown as "Already added to cart"
        ArrayList<Integer> productIdList = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_Quotation);
        productIdList.add(productId);
        App.db().putListInt(Keys.PRODUCT_ID_LIST_IN_Quotation, productIdList);

        //to store product items so that its easy to show them directly in cart items listing activity
        ArrayList<WcProduct> productList = App.db().getListCartItem(Keys.PRODUCT_LIST_IN_Quotation);
        productList.add(product);
        App.db().putListCartItems(Keys.PRODUCT_LIST_IN_Quotation, productList);
    }

    public static ArrayList<Integer> getBasketItemsList(Activity activity) {
        ArrayList<Integer> list = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_BASKET);
        return list;
    }

    public static void addProductToWishlist(Activity activity, int productId) {
        ArrayList<Integer> list = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_WISHLIST);
        list.add(productId);
        App.db().putListInt(Keys.PRODUCT_ID_LIST_IN_WISHLIST, list);
    }

    public static ArrayList<Integer> getWishlistItemsList(Activity activity) {
        ArrayList<Integer> list = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_WISHLIST);
        return list;
    }

    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if (list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }

    public static ArrayList<Integer> emptyProductIDsFromBasketList(Activity activity) {
        ArrayList<Integer> list = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_BASKET);
        list.removeAll(list);
        return list;
    }

    public static ArrayList<WcProduct> emptyProductsFromBasketList(Activity activity) {
        ArrayList<WcProduct> list = App.db().getListCartItem(Keys.PRODUCT_LIST_IN_WC_BASKET);
        list.removeAll(list);
        return list;
    }

    public static ArrayList<Integer> emptyProductIDsQuotationList(Activity activity) {
        ArrayList<Integer> list = App.db().getListInt(Keys.PRODUCT_ID_LIST_IN_Quotation);
        list.removeAll(list);
        return list;
    }

    public static ArrayList<WcProduct> emptyProductsFromQuotationList(Activity activity) {
        ArrayList<WcProduct> list = App.db().getListCartItem(Keys.PRODUCT_LIST_IN_Quotation);
        list.removeAll(list);
        return list;
    }


    public static String[] getNextPageNo(String url) {
        Uri uri = Uri.parse(url);
        String protocol = uri.getScheme();
        String server = uri.getAuthority();
        String path = uri.getPath();
        Set<String> args = uri.getQueryParameterNames();
        String page = uri.getQueryParameter("page");
        String title = uri.getQueryParameter("title");
        return new String[]{page, title};
        // return page;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static boolean isViberClientInstalled(Context context) {
        PackageManager myPackageMgr = context.getPackageManager();
        try {
            myPackageMgr.getPackageInfo("com.viber.voip", PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }

    public static void goToViberMarket(Context ctx) {

        Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=com.viber.voip");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(myIntent);

        return;
    }

    public static List<WcCategory> getCategoryListSortedByCount(List<WcCategory> categoryList) {   //to sort the list in alphabetical order
        final List<WcCategory> categories = categoryList;

        Collections.sort(categories, new Comparator<WcCategory>() {
            @Override
            public int compare(WcCategory a1, WcCategory a2) {
                return a2.count - a1.count; // Ascending
            }
        });
        return categories;
    }

    public static void getLatestProducts(List<WcProduct> productList) throws android.net.ParseException {
        final List<WcProduct> list = productList;
//        list.add(new WcProduct("John", new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2010")));
//        list.add(new Person("Tom", new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1990")));
//        list.add(new Person("Bob", new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000")));

        System.out.println("Before Sort");
        for (WcProduct person : list) {
            Log.d("sort", "getLatestProducts: before sort " + person.id);
            System.out.println(person.title + " " + person.id);
        }

        // Sort in assending order
        Collections.sort(list, new Comparator<WcProduct>() {
            public int compare(WcProduct p1, WcProduct p2) {
                return String.valueOf(p1.created_at).compareTo(p2.created_at);
            }
        });

        System.out.println("After Assending sort");
        for (WcProduct person : list) {
            Log.d("sort", "getLatestProducts: after ascending sort " + person.id);
            System.out.println(person.created_at + " " + person.created_at);
        }

        // Sort in dessending order
        Collections.sort(list, new Comparator<WcProduct>() {
            public int compare(WcProduct p1, WcProduct p2) {
                return String.valueOf(p2.created_at).compareTo(p1.created_at);
            }
        });

        System.out.println("After Descending sort");
        for (WcProduct person : list) {
            Log.d("sort", "getLatestProducts: after descending sort " + person.id);
            System.out.println(person.created_at + " " + person.created_at);
        }
    }
}
