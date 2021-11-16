package com.example.myapplication.webservices;

import com.example.myapplication.ResponseModel.matchesresponse.ResultsMatchesResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

//    @FormUrlEncoded
    @GET("api")
    Call<ResultsMatchesResponseModel> getRandomUserRequest(@Query("results") String results);

}
