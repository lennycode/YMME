package com.example.lenny.ymme.network;

import android.util.Log;

import com.example.lenny.ymme.MessageEvent;
import com.example.lenny.ymme.datainterface.YMMEAPI;
import com.example.lenny.ymme.models.Engines;
import com.example.lenny.ymme.models.Makes;
import com.example.lenny.ymme.models.Models;
import com.example.lenny.ymme.models.Years;
import com.example.lenny.ymme.util.Config;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by lenny on 5/22/17.
 */

public class APILoader {
    private static final String TAG = "APILoader";

    private YMMEAPI buildClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //Removed, causing an exception.
        //httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Config.APIEndppoint)
                .build();

        return retrofit.create(YMMEAPI.class);

    }
    private YMMEAPI buildClientrx() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //Removed, causing an exception.
        //httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Config.APIEndppoint)
                .build();

        return retrofit.create(YMMEAPI.class);

    }


    public void loadYearsSorted(){

        buildClientrx().loadYearsSorted("years").subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(s->{
                    List<String> toBeSorted = s.getYears();
                    Collections.reverse(toBeSorted);
                    s.setYears(toBeSorted);
                    return Observable.just(s);
                })
                .subscribe(s->{
                    EventBus.getDefault().post(new MessageEvent<Years>((Years) s));
                    Log.e(TAG, "Success");
                }    );



    }

    public void loadYears() {
            loadYearsSorted();

    }

    public void loadModels(int year, String make) {

        buildClient().loadModels("models", year, make).enqueue(new Callback<Models>() {
               @Override
               public void onResponse(Call<Models> call, Response<Models> response) {

                   EventBus.getDefault().post(new MessageEvent<Models>((Models) response.body()));
                   Log.e(TAG, "Success");
               }

               @Override
               public void onFailure(Call<Models> call, Throwable t) {
                   Log.e("RestError", t.getMessage());
               }
           }

        );
    }


    public void loadEngines(int year, String make, String model) {

        buildClient().loadEngines("engines", year, make, model).enqueue(new Callback<Engines>() {
                @Override
                public void onResponse(Call<Engines> call, Response<Engines> response) {

                    EventBus.getDefault().post(new MessageEvent<Engines>((Engines) response.body()));
                    Log.e(TAG, "Success");
                }

                @Override
                public void onFailure(Call<Engines> call, Throwable t) {

                    Log.e("RestError", t.getMessage());
                }
            }

        );
    }

    public void loadMakes(int year) {

        buildClient().loadMakes("makes", year).enqueue(new Callback<Makes>() {
                   @Override
                   public void onResponse(Call<Makes> call, Response<Makes> response) {
                       EventBus.getDefault().post(new MessageEvent<Makes>((Makes) response.body()));
                       Log.e(TAG, "Success");
                   }

                   @Override
                   public void onFailure(Call<Makes> call, Throwable t) {

                       Log.e("RestError", t.getMessage());
                   }
               }

        );
    }

}