package com.demo.myflower.api;
/* Created by Ihor Bochkor on 18.10.2020.
 */
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Api api;
    private Retrofit retrofit;
    private Api(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://app-demo-web-august.web.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Api getInstance(){
        if (api == null){
            api = new Api();
        }
        return api;
    }

    public ApiService apiService(){
        return retrofit.create(ApiService.class);
    }

}
