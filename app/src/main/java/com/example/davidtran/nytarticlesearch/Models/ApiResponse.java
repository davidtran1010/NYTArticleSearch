package com.example.davidtran.nytarticlesearch.Models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by davidtran on 6/21/17.
 */

public class ApiResponse {

    @SerializedName("response")
    public JsonObject response;

    @SerializedName("status")
    public String status;

    public JsonObject getResponse() {
        if(response == null) return new JsonObject();
        else{
            return response;
        }

    }

    public void setResponse(JsonObject response) {
        this.response = response;
    }

  /*  public String getStatus() {
        return status;
    }*/

}
