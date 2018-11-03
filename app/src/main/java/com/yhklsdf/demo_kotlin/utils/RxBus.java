package com.yhklsdf.demo_kotlin.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * 非常好用的一个工具类，回头看一看它的实现原理
 */
public final class RxBus {
    private final PublishSubject<Object> bus = PublishSubject.create();
    private static RxBus instance;

    private RxBus(){}

    public void send(final Object event) {
        bus.onNext(event);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }

    public static RxBus getInstance() {
        if (instance==null){
            instance=new RxBus();
        }
        return instance;
    }
}

