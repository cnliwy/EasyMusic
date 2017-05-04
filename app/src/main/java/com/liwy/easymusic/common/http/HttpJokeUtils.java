package com.liwy.easymusic.common.http;

import com.liwy.easymusic.model.OnlineMusicList;
import com.liwy.easymusic.model.happy.BaseHappyResult;
import com.liwy.easymusic.model.happy.JokeResult;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liwy on 2017/5/4.
 */

public class HttpJokeUtils {
    private static final String BASE_URL = "http://route.showapi.com/";
    private static final String showapi_appid = "37278";
    private static final String showapi_app_secret = "63d6dc0aa6e1447f85caf2678d2c3956";
    private static final String showapi_sign = showapi_app_secret;
    private static final int maxResult = 15;
    private static final int DEFAULT_TIMEOUT = 5;//默认超时时间
    private Retrofit retrofit;
    private HttpApi httpService;



    //构造方法私有
    private HttpJokeUtils() {
//        .addHeader("user-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request broswerRequest = originalRequest.newBuilder()
                        .header("user-agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                        .build();
                return chain.proceed(broswerRequest);
            }
        });
        // json解析实例
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        httpService = retrofit.create(HttpApi.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpJokeUtils INSTANCE = new HttpJokeUtils();
    }

    //获取单例
    public static HttpJokeUtils getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取笑话
     * @param time
     * @param page
     * @param subscriber
     */
    public void getJokes(String time, String page, Subscriber<BaseHappyResult<JokeResult>> subscriber){
        httpService.getJokes(time,page,String.valueOf(maxResult),showapi_appid,showapi_sign)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取笑话
     * @param page
     * @param subscriber
     */
    public void getGifJokes(String page, Subscriber<BaseHappyResult<JokeResult>> subscriber){
        httpService.getGifJokes(page,String.valueOf(maxResult),showapi_appid,showapi_sign)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
