package com.susankya.swadesibidhesi.application;

import android.app.Application;
import android.util.Base64;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;
import com.onesignal.OneSignal;
import com.susankya.swadesibidhesi.BuildConfig;
import com.susankya.swadesibidhesi.Generic.Client;
import com.susankya.swadesibidhesi.models.WooCommerce.WcProduct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.susankya.swadesibidhesi.Generic.Flavor.MBSH;
import static com.susankya.swadesibidhesi.Generic.Keys.USER_LOGGED_IN;

public class App extends Application {
    private static String LOGIN_BASE_URL;
    private static String WC_BASE_URL = Client.HTTPS + Client.GENERIC_CLIENT + Client.BASE_URL_API;
    public static TinyDB tinyDB;
    public static List<WcProduct> wcCartProducts = new ArrayList<>();
    private static String credentials = "ck_f1f887d789a2bc9f7e1fa0e11583192bb1669e9c" + ":" + "cs_95382a5d8d11fbcc1ff6d764917cbb3b1d189e7c";

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.FLAVOR.equals(MBSH)) {
            LOGIN_BASE_URL = "https://mbshnepal.com/";
            credentials = "ck_f1f887d789a2bc9f7e1fa0e11583192bb1669e9c" + ":" + "cs_95382a5d8d11fbcc1ff6d764917cbb3b1d189e7c";
            WC_BASE_URL = Client.HTTPS + Client.MBSH_CLIENT + Client.BASE_URL_API;
        }
        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        tinyDB = new TinyDB(getApplicationContext());
        Fresco.initialize(this);  //Fresco initialization
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new TypiconsModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new MeteoconsModule())
                .with(new WeathericonsModule())
                .with(new SimpleLineIconsModule())
                .with(new IoniconsModule());
    }

    public static TinyDB db() {
        return tinyDB;
    }

    public static Retrofit getWcRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES);

        httpClient.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(WC_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("Authorization", string)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        builder.client(okHttpBuilder.build());
//        }
        Retrofit retrofit = builder.build();
        return retrofit;
    }

    public static Retrofit getShippingRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES);

        httpClient.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LOGIN_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("Authorization", string)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        builder.client(okHttpBuilder.build());
//        }
        Retrofit retrofit = builder.build();
        return retrofit;
    }

    public static Retrofit getRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES);

        httpClient.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(LOGIN_BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create());

        if (App.db().getBoolean(USER_LOGGED_IN)) {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });
            builder.client(okHttpBuilder.build());
        }

        Retrofit retrofit = builder.build();
        return retrofit;
    }
}