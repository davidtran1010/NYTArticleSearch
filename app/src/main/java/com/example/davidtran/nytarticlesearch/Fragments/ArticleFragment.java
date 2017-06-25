package com.example.davidtran.nytarticlesearch.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.davidtran.nytarticlesearch.Activities.WebViewActivity;
import com.example.davidtran.nytarticlesearch.Adapters.ArticleAdapter;
import com.example.davidtran.nytarticlesearch.Models.Article;
import com.example.davidtran.nytarticlesearch.Models.RecyclerItemClickListener;
import com.example.davidtran.nytarticlesearch.Models.SearchArticleResult;
import com.example.davidtran.nytarticlesearch.Models.SearchFilter;
import com.example.davidtran.nytarticlesearch.Models.SearchRequest;
import com.example.davidtran.nytarticlesearch.R;
import com.example.davidtran.nytarticlesearch.Utils.ArticleApi;
import com.example.davidtran.nytarticlesearch.Utils.RetrofitUtil;
import com.github.kimkevin.cachepot.CachePot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;

/**
 * Created by davidtran on 6/21/17.
 */

public class ArticleFragment extends Fragment {
    SearchRequest searchRequest;
    List<Article> articleList;
    ArticleAdapter articleAdapter;

    @BindView(R.id.metroList_Acticle)
    RecyclerView rcArticleList;
    @BindView(R.id.loadingBar)
    ProgressBar loadingBar;
    SearchFilter searchFilter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_articles, container, false);

        ButterKnife.bind(this, view);
        articleList = new ArrayList<Article>();
        String squery = "";

        try {
            squery = getArguments().getString("query");
            Log.i("My log: query:",squery);

        } catch (Exception e) {
            e.printStackTrace();
            squery = "";
            Log.i("My log: query:",squery);
        }
        try {

            searchFilter = getArguments().getParcelable("searchfilter");

            Log.i("My log: filter:", searchFilter.getBeginDate() + ":" + searchFilter.getOrder()+
                    ": sports:" + searchFilter.getHasSport()+" : arts:"+searchFilter.getHasArt() + ": fashion:" + searchFilter.getHasFashion());
        } catch (Exception e) {
            e.printStackTrace();
            searchFilter = new SearchFilter();
            Log.i("My log: filter:", searchFilter.getBeginDate() + ":" + searchFilter.getOrder()+
                    ": sports:" + searchFilter.getHasSport()+" : arts:"+searchFilter.getHasArt() + ": fashion:" + searchFilter.getHasFashion());
        }

        fetchData(setUpSearchRequest(squery,searchFilter));
        setUpListener();
        return view;
    }
    private void setUpListener(){
        rcArticleList.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Article article = articleList.get(position);
                        viewArticleWeb(article.getWebUrl());
                    }
                })
        );
    }
    private void viewArticleWeb(String url){
        Intent intent = new Intent(getActivity(), WebViewActivity.class);

        intent.putExtra("DetailWebViewUrl",url);
        startActivity(intent);
    }
    private void setUpAdapter(@Nullable List<Article> articleList) {
        if(articleList.size()==0){
            articleList.add(new Article());
        }
        articleAdapter = new ArticleAdapter(articleList, getContext());
        rcArticleList.setAdapter(articleAdapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, VERTICAL);
        rcArticleList.setLayoutManager(layoutManager);
    }

    private SearchRequest setUpSearchRequest(String query, SearchFilter searchFilter) {
        SearchRequest searchRequest = new SearchRequest();
        String Desk = "";

        if(query!="") searchRequest.setQuery(query);
        if (searchFilter.getBeginDate() != "") {
            if (searchFilter.getHasArt()) Desk += "\"Arts\"";
            if (searchFilter.getHasSport()) Desk += "\"Sports\"";
            if (searchFilter.getHasFashion()) Desk += "\"Fashion\"";

            searchRequest.setDesk(Desk);
            searchRequest.setBeginDate(searchFilter.getBeginDate());
            searchRequest.setSort(searchFilter.getOrder());
        }


        return searchRequest;
    }

    /* private boolean isSearchWithFilter(){
         if (searchFilter.getOrder()!="") return true;
         return false;
     }*/
    private void fetchData(SearchRequest searchRequest) {

        final ArticleApi articleapi = RetrofitUtil.create().create(ArticleApi.class);
        articleapi.search(searchRequest.toQueryMap()).enqueue(new Callback<SearchArticleResult>() {
            @Override
            public void onResponse(Call<SearchArticleResult> call, Response<SearchArticleResult> response) {

                Log.d("Log:connecion status:", response.message());
                Log.d("My log: docs:", new Gson().toJson(response.body()));
                try {
                    articleList = response.body().getArticleList();
                    for (Article a : articleList) {
                        Log.d("My log: Article ", a.getSnipet());
                    }
                    if(articleList.size()>0) {
                        setUpAdapter(articleList);
                    }
                    else{
                        Toast.makeText(getActivity(),"Your searching not found! \nAll article will be reloaded",Toast.LENGTH_SHORT);
                        reFeshFragment();
                    }
                }
                catch (Exception e){
                    articleList = new ArrayList<Article>();
                    Toast.makeText(getActivity(),"Your searching not found! \nAll article will be reloaded",Toast.LENGTH_LONG);
                    reFeshFragment();
                }

                loadingBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<SearchArticleResult> call, Throwable t) {
                Log.i("Loi:", t.toString());
            }
        });

    }
    private  void reFeshFragment(){
        loadingBar.setVisibility(View.VISIBLE);
        Fragment fragment = new ArticleFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container, fragment).addToBackStack(null).commit();
        loadingBar.setVisibility(View.GONE);
    }


}
