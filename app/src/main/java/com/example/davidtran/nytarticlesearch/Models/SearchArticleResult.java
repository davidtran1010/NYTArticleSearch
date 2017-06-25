package com.example.davidtran.nytarticlesearch.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by davidtran on 6/21/17.
 */

public class SearchArticleResult {
    @SerializedName("docs") private List<Article> articleList;

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

}
