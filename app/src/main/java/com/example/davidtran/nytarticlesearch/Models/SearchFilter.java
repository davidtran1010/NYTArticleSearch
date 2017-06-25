package com.example.davidtran.nytarticlesearch.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by davidtran on 6/24/17.
 */

public  class SearchFilter implements Parcelable {


    String BeginDate;
    String Order;
    boolean hasArt;
    boolean hasSport;
    boolean hasFashion;

    public SearchFilter() {
        BeginDate = "";
        Order = "";
        hasArt = false;
        hasSport = false;
        hasFashion = false;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public String getOrder() {
        return Order;
    }

    public boolean getHasArt() {
        return hasArt;
    }

    public boolean getHasSport() {
        return hasSport;
    }

    public boolean getHasFashion() {
        return hasFashion;
    }

    public SearchFilter(String beginDate, String order, boolean hasArt, boolean hasSport, boolean hasFashion) {
        BeginDate = beginDate;
        Order = order;
        this.hasArt = hasArt;
        this.hasSport = hasSport;
        this.hasFashion = hasFashion;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.BeginDate);
        dest.writeString(this.Order);
        dest.writeByte(this.hasArt ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasSport ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasFashion ? (byte) 1 : (byte) 0);
    }

    protected SearchFilter(Parcel in) {
        this.BeginDate = in.readString();
        this.Order = in.readString();
        this.hasArt = in.readByte() != 0;
        this.hasSport = in.readByte() != 0;
        this.hasFashion = in.readByte() != 0;
    }

    public static final Creator<SearchFilter> CREATOR = new Creator<SearchFilter>() {
        @Override
        public SearchFilter createFromParcel(Parcel source) {
            return new SearchFilter(source);
        }

        @Override
        public SearchFilter[] newArray(int size) {
            return new SearchFilter[size];
        }
    };
}
