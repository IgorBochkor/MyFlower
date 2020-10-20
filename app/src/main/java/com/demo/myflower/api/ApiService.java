package com.demo.myflower.api;
/* Created by Ihor Bochkor on 18.10.2020.
 */

import com.demo.myflower.pojo.Flower;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("flower.json")
    Observable<List<Flower>> getFlower();
}
