package com.liwy.easymusic.common.http;


import com.liwy.easymusic.entity.MusicRoot;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liwy on 2017/3/14.
 */

public class HttpUtils {
    public static final String BASE_URL = "https://api.douban.com/";
    private static final int DEFAULT_TIMEOUT = 5;//默认超时时间
    private Retrofit retrofit;
    private HttpApi httpService;



    //构造方法私有
    private HttpUtils() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
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
        private static final HttpUtils INSTANCE = new HttpUtils();
    }

    //获取单例
    public static HttpUtils getInstance(){
        return SingletonHolder.INSTANCE;
    }

    // 获取音乐列表
    public void searchMusicByTag(String tag, Subscriber<MusicRoot> subscriber){
        httpService.searchMusicByTag(tag).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }


    public static String Music_Titles []={"流行","经典","韩系","欧美"};
    public static String PopulayTag[] ={"歌声", "青春", "回忆", "孙燕姿","周杰伦","林俊杰", "陈奕迅", "王力宏", "邓紫棋","风声",  "海边", "童话", "美女",  "一生", "爱", "爱情", "远方", "缘分","天空","张国荣","黄家驹","　beyond", "黑豹乐队" };
    public static String ClassicTag []={"张国荣","黄家驹","　beyond", "黑豹乐队", "王菲", "五月天", "陈奕迅", "古巨基", "杨千嬅", "叶倩文", "许嵩","刘德华","邓丽君","张学友"};
    public static String KereaTag[] ={"bigbang","rain", "PSY", "李弘基", "李承哲","金钟国", "李孝利", "孝琳", "IU", "EXO", "T-ara", "东方神起", "Epik High", "Girl's Day", " 紫雨林", "Zebra"};
    public static String AmericanTag[] ={"Jay-Z","Justin Bieber","James Blunt","Eminem","Akon","Adele","Avril Lavigne","Beyoncé","Lady GaGa","Taylor Swift","Alicia Keys","Owl City","Coldplay"};


    public static String[] getApiTag(int pos){
        switch (pos){
            case 0:
                return PopulayTag;
            case 1:
                return ClassicTag;
            case 2:
                return KereaTag;
            case 3:
                return AmericanTag;



        }
        return null;
    }


    public static  String getRandomTAG(List<String> list){
        Random random=new Random();
        int i=random.nextInt(list.size());
        return  list.get(i);
    }

}
