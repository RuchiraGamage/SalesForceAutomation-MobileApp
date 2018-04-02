package com.example.salinda.salseforseautomation.rest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://salesforcenew20180208102258.azurewebsites.net/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        //.header("Authorization", "Basic "+SessionHandler.getUserDetails().getToken())
                        //.header("Ocp-Apim-Subscription-Key", "6a84aaaf0809437cba1cb6e77b206be3")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        }
        return retrofit;
    }
}
