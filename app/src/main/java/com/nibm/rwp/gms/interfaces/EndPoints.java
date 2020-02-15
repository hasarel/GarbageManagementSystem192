package com.nibm.rwp.gms.interfaces;

import com.google.gson.JsonElement;
import com.nibm.rwp.gms.dto.CustomerData;
import com.nibm.rwp.gms.dto.CustomerRequest;
import com.nibm.rwp.gms.dto.GarbageCategoryList;
import com.nibm.rwp.gms.dto.GarbageRequest;
import com.nibm.rwp.gms.dto.PaymentGatway;
import com.nibm.rwp.gms.dto.PaymentHistory;
import com.nibm.rwp.gms.dto.RequestHistory;
import com.nibm.rwp.gms.dto.UcArea;
import com.nibm.rwp.gms.dto.UcVehicleList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface EndPoints {


    //maharagama uc area
    @GET("areas/all")
    Call<List<UcArea>> getAllUcArea();

    //garbage category list
    @GET("/category/all")
    Call<List<GarbageCategoryList>> getGarbageList();

    //set location to driver
    @GET("/driver/request/get")
    Call<List<CustomerRequest>> getDriverMap();

    // customer request history list
    @GET("/customer/all/request")
    Call<List<RequestHistory>> getRequestHistory();

    // customer payment history list
    @GET("/customer/all/request")
    Call<List<PaymentHistory>> getPaymentHistory();

//    @GET("/customer/all/request/")
//    Call<List<PaymentHistory>> getPaymentHistory(@Url String url);

    // garbage vehicle type list
    @GET("/vehicle/types")
    Call<List<UcVehicleList>> getUcVehicle();

    // customer register
    @POST("/customer/create")
    Call<JsonElement> setCustomerData(@Body CustomerData Data);

    // customer request
    @POST("/customer/request")
    Call<JsonElement> setCustomerRequest(@Body GarbageRequest req);

    //payment transaction
    @POST("/payment/approve")
    Call<JsonElement> setPaymentGatway(@Body PaymentGatway paymentGatway);

}
