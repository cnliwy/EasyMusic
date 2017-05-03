package com.liwy.easymusic.common.http;


import com.liwy.easymusic.entity.MovieEntity;
import com.liwy.easymusic.entity.MusicRoot;
import com.liwy.easymusic.entity.Musics;
import com.liwy.easymusic.model.OnlineMusicList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liwy on 2017/3/14.
 */

public interface HttpApi {

    @GET("top250")
    Observable<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("v2/music/search")
    Observable<MusicRoot> searchMusicByTag(@Query("tag") String tag);

    @GET("v2/music/{id}")
    Observable<Musics> getMusicDetail(@Path("id") String id);

    @GET("v1/restserver/ting")
    Observable<OnlineMusicList> searchMusicByConditions(@Query("type")String type, @Query("size")int size, @Query("offset")int offset, @Query("method")String method);



}
