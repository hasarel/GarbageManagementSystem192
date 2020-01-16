package com.nibm.rwp.gms.interfaces;

import com.nibm.rwp.gms.dto.GarbageCategoryList;
import com.nibm.rwp.gms.dto.GarbageRequest;
import com.nibm.rwp.gms.dto.UcArea;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EndPoints {

    //maharagama uc area
    @GET("areas/all")
    Call<List<UcArea>> getAllUcArea();


    @GET("/category/all")
    Call<List<GarbageCategoryList>> getGarbageList();

//    @POST("")
//    Call<GarbageRequest> setCustomerRequest(@Body GarbageRequest garbageRequest);

}
