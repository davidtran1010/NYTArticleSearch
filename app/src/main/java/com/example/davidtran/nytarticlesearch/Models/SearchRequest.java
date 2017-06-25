package com.example.davidtran.nytarticlesearch.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.http.QueryMap;

/**
 * Created by davidtran on 6/21/17.
 */

public class SearchRequest implements Parcelable {


    private String Query = "";
    private String BeginDate = "";
    private String Sort = "newest";
    private String Desk = "";
    private int Page = 0;

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        Page = page;
    }



    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(String beginDate) {
        BeginDate = beginDate;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }

    public String getDesk() {
        return Desk;
    }

    public void setDesk(String desk) {
        Desk = desk;
    }

    public Map<String, String> toQueryMap() {
        Map<String, String> options = new HashMap<>();
        options.put("Page",""+Page);
        if (Query != "") {
            options.put("q", Query);
        }
        if (BeginDate != "")
            options.put("begin_date", BeginDate);
        if (Sort != "") {
            options.put("sort", Sort);
        }
        if (Desk != "") {
            options.put("fq", "news_desk:(" + Desk + ")");
        }


        return options;
    }

    public SearchRequest() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Query);
        dest.writeString(this.BeginDate);
        dest.writeString(this.Sort);
        dest.writeString(this.Desk);
        dest.writeInt(this.Page);
    }

    protected SearchRequest(Parcel in) {
        this.Query = in.readString();
        this.BeginDate = in.readString();
        this.Sort = in.readString();
        this.Desk = in.readString();
        this.Page = in.readInt();
    }

    public static final Creator<SearchRequest> CREATOR = new Creator<SearchRequest>() {
        @Override
        public SearchRequest createFromParcel(Parcel source) {
            return new SearchRequest(source);
        }

        @Override
        public SearchRequest[] newArray(int size) {
            return new SearchRequest[size];
        }
    };
}