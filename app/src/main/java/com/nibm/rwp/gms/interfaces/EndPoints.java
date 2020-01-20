package com.nibm.rwp.gms.interfaces;

import com.nibm.rwp.gms.dto.GarbageCategoryList;
import com.nibm.rwp.gms.dto.GarbageRequest;
import com.nibm.rwp.gms.dto.UcArea;
import com.nibm.rwp.gms.dto.UcVehicleList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EndPoints {

    //maharagama uc area
    @GET("areas/all")
    Call<List<UcArea>> getAllUcArea();

    @GET("/category/all")
    Call<List<GarbageCategoryList>> getGarbageList();

    @GET("/vehicle/types")
    Call<List<UcVehicleList>> getUcVehicle();

    @POST("/customer/request")
    @FormUrlEncoded
    Call<GarbageRequest> setCustomerRequest(
            @Field("customer_name") String customer_name, @Field("email") String email,
            @Field("area_id") String area_id, @Field("longitude") String longitude,
            @Field("latitude") String latitude, @Field("address_1") String address_1,
            @Field("address_2") String address_2, @Field("address_3") String address_3,
            @Field("tele_no") String tele_no, @Field("description") String description,
            @Field("category_id") String category_id, @Field("vehicle_type_id") String vehicle_type_id);

    @POST("/customer/request")
    Call<GarbageRequest> setCustomerRequest(GarbageRequest garbageRequestModel);


}
