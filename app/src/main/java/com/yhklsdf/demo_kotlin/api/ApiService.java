package com.yhklsdf.demo_kotlin.api;

import com.yhklsdf.demo_kotlin.gson.History;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("appstore/history/query?key=292e01a8067e4&day={day}")
    Observable<History> getHistory(@Path("day") String day);
}
