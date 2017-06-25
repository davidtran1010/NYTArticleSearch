package com.example.davidtran.nytarticlesearch.Utils;

import android.util.Log;

import com.example.davidtran.nytarticlesearch.BuildConfig;
import com.example.davidtran.nytarticlesearch.Models.ApiResponse;
import okhttp3.MediaType;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by davidtran on 6/21/17.
 */


public class RetrofitUtil {
    public static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/";
    private static final String API_KEY= BuildConfig.API_KEY;
    private static final okhttp3.MediaType JSON = okhttp3.MediaType.parse("application/json; charset=utf-8");
    private static final Gson GSON = new Gson();
    public static Retrofit create(){
    return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client())
            .baseUrl(BASE_URL)
            .build();
}

    private static OkHttpClient client() {
    return new OkHttpClient.Builder()
                .addInterceptor(apikeyInterceptor())
                .addInterceptor(apiResponseinterceptor())
                .build();
    }
    private static Interceptor apiResponseinterceptor(){
        return new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request resquest = chain.request();
                Response response = chain.proceed(resquest);
                Log.d("Log url:",resquest.url().toString());
                ApiResponse apiResponse = GSON.fromJson(response.body().string(),ApiResponse.class);
                apiResponse.getResponse();

                return response.newBuilder()
                      .body(ResponseBody.create(JSON,GSON.toJson(apiResponse.getResponse())))
                        .build();

            }
        };
    }
    private static Interceptor apikeyInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("api_key","2d29509e38fb425ea53428b3ef6d287e")
                        .build();

                request = request.newBuilder()
                        .url(url)
                        .build();
                return chain.proceed(request);
            }
        };
        }
    }


