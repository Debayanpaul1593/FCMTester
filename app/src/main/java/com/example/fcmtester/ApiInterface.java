package com.example.fcmtester;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("1hh8vh7whv6cjme/list1.json?dl=1")
    Call<ResponseBody> getUsers();


    @GET("ep7v5yex3fjs3s1/webview.json?dl=1")
    Call<ResponseBody> getWebViews();

    @GET("n4i57r22rdx89cw/list2.json?dl=1")
    Call<ResponseBody> getGraphs();
}
