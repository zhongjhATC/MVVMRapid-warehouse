package com.yutuo.warehouse.data.http.retrofit;

import android.text.TextUtils;

import com.yutuo.warehouse.BuildConfig;
import com.zhongjh.mvvmrapid.http.HttpsUtils;
import com.zhongjh.mvvmrapid.http.cookie.CookieJarImpl;
import com.zhongjh.mvvmrapid.http.cookie.store.PersistentCookieStore;
import com.zhongjh.mvvmrapid.http.interceptor.BaseInterceptor;
import com.zhongjh.mvvmrapid.http.interceptor.CacheInterceptor;
import com.zhongjh.mvvmrapid.http.interceptor.logging.Level;
import com.zhongjh.mvvmrapid.http.interceptor.logging.LoggingInterceptor;
import com.zhongjh.mvvmrapid.utils.Utils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author zhongjh
 * @date 2021/3/25
 * 实现网络请求
 */
public class RetrofitClient {

    /**
     * 超时时间
     */
    private static final int DEFAULT_TIMEOUT = 20;
    /**
     * 服务端根路径
     */
    public static String baseUrl = "https://www.wanandroid.com/";

    /**
     * retrofit本身
     */
    private final Retrofit retrofit;

    /**
     * 缓存
     */
    private File httpCacheDirectory;

    private static class SingletonHolder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitClient() {
        this(baseUrl, null);
    }

    /**
     * @param url     地址
     * @param headers 请求头
     */
    private RetrofitClient(String url, Map<String, String> headers) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(Utils.getContext().getCacheDir(), "goldze_cache");
        }

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        //                .cache(cache)
        //构建者模式
        //是否开启日志打印
        //打印的等级
        // 打印类型
        // request的Tag
        // Response的Tag
        // 添加打印头, 注意 key 和 value 都不能是中文
        // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(Utils.getContext())))
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new CacheInterceptor(Utils.getContext()))
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .addInterceptor(new LoggingInterceptor
                        .Builder()//构建者模式
                        // 是否开启日志打印
                        .loggable(BuildConfig.DEBUG)
                        // 打印的等级
                        .setLevel(Level.BASIC)
                        // 打印类型
                        .log(Platform.INFO)
                        // request的Tag
                        .request("Request")
                        // Response的Tag
                        .response("Response")
                        // 添加打印头, 注意 key 和 value 都不能是中文
                        .addHeader("log-header", "I am the log request header.")
                        .build()
                )
                .retryOnConnectionFailure(false)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
//                .addConverterFactory(CustomConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    /**
     * /**
     * execute your customer API
     * For example:
     * MyApiService service =
     * RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
     * <p>
     * RetrofitClient.getInstance(MainActivity.this)
     * .execute(service.lgon("name", "password"), subscriber)
     * * @param subscriber
     */

    public static <T> T execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        return null;
    }
}
