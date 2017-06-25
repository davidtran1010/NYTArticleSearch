package com.example.davidtran.nytarticlesearch.Utils;



import android.util.Log;

import com.example.davidtran.nytarticlesearch.Models.SearchArticleResult;

import java.util.Map;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by davidtran on 6/21/17.
 */

public interface ArticleApi {
@GET("articlesearch.json") Call<SearchArticleResult> search(@QueryMap(encoded = true)Map<String,String> options);

        }
