package com.example.workshop2;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/api/users/signin")
    Call<ActivityLogin> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/users/register")
    Call<Void> executeSignup (@Body HashMap<String, String>map);

    @POST("/api/products")
    Call<Void> executeAddProduct(@Body HashMap<String, String> map);

    @GET("/api/products")
    Call<ActivityMostrarProductos> executeShowProducts(@Body HashMap<String, String> map);

}
