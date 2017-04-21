package com.liwy.easymusic.common;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by liwy on 2017/3/13.
 */

public class RxBus {
    private HashMap<Object,List<Subject>> maps = new HashMap<Object,List<Subject>>();
    private HashMap<Object,Object> dataCache = new HashMap<Object,Object>();
    private static RxBus instance;

    // 获取单例对象
    public static RxBus getInstance(){
        if (instance == null){
            synchronized (RxBus.class){
                if (instance == null){
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }


    // 注册事件
    public <T> Observable<T> register(@NonNull Object tag, @NonNull Class<T> clazz){
        List<Subject> subjects =  maps.get(tag);
        if (subjects == null){
            subjects = new ArrayList<Subject>();
            maps.put(tag,subjects);
        }
        PublishSubject<T> subject = PublishSubject.<T>create();
        subjects.add(subject);
        return subject;
    }
    //取消注册
    public void unregister(@NonNull Object tag, @NonNull Observable observable){
        List<Subject> subjects = maps.get(tag);
        if (subjects != null){
            subjects.remove((Subject)observable);
            if (subjects.isEmpty()){
                maps.remove(tag);
            }
        }
    }
    /**
     * 用于先发送事件后注册事件的使用情况，注册后调用此方法即可获取历史数据
     * @param tag 事件tag
     */
    public void getData(@NonNull Object tag){
        // 先查询缓存里有没有数据
        Object obj = dataCache.get(tag);
        if (obj != null){
            List<Subject> subjects = maps.get(tag);
            if (subjects != null && subjects.size() > 0){
                for (Subject subject : subjects){
                    subject.onNext(obj);
                }
            }
        }
    }

    // 发送消息至已注册的事件,tag就是该数据的对象名称
    public void post(@NonNull Object o){
        post(o.getClass().getSimpleName(), o);
    }

    // 给同属同一tag的事件群发消息
    public void post(@NonNull Object tag, @NonNull Object o){
        List<Subject> subjects = maps.get(tag);
        if (subjects != null || !subjects.isEmpty()){
            for (Subject subject : subjects){
                subject.onNext(o);
            }
        }
    }

    //给所有的发送注册事件发送消息
    public void postAll(@NonNull Object o){
        if (maps != null){
            // 遍历整个maps
            for (Object key : maps.entrySet()){
                post(key,o);
            }
        }
    }
}
