package com.example.davidtran.nytarticlesearch.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by davidtran on 6/21/17.
 */

public class Article {
    @SerializedName("snippet") public String snipet;
    @SerializedName("web_url") public String webUrl;

    public Article() {
       snipet = "Nothing to show!";
    }

    public String getSnipet() {
        return snipet;
    }

    public void setSnipet(String snipet) {
        this.snipet = snipet;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public List<Media> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Media> multimedia) {
        this.multimedia = multimedia;
    }

    @SerializedName("multimedia") public List<Media> multimedia;
    public static class Media{
        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getUrl() {
            return "http://www.nytimes.com/"+url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        private int width;
        private int height;
        private String url;

    }


}
