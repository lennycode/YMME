package com.example.lenny.ymme.datainterface;

import com.example.lenny.ymme.models.Engines;
import com.example.lenny.ymme.models.Makes;
import com.example.lenny.ymme.models.Models;
import com.example.lenny.ymme.models.Years;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenny on 5/21/17.
 */

public interface YMMEAPI {
    //http://grocerygetter.x10host.com/vehendpoint.php?mode=years
    //Leading slash resolves to root domain
    @GET("vehendpoint.php")
    Call<Years> loadYears(@Query("mode") String mode);

    @GET("vehendpoint.php")
    io.reactivex.Observable<Years> loadYearsSorted(@Query("mode") String mode);

    @GET("vehendpoint.php")
    Call<Makes> loadMakes(@Query("mode") String mode,
                          @Query("year") int year);

    @GET("vehendpoint.php")
    Call<Models> loadModels(@Query("mode") String mode,
                            @Query("year") int year,
                            @Query("make") String make);

    @GET("vehendpoint.php")
    Call<Engines> loadEngines(@Query("mode") String mode,
                              @Query("year") int year,
                              @Query("make") String make,
                              @Query("model") String model);

}
