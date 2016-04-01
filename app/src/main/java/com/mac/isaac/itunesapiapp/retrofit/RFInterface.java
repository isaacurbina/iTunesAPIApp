package com.mac.isaac.itunesapiapp.retrofit;

import com.mac.isaac.itunesapiapp.pojos.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RFInterface {

    @GET("search")
    Call<Results> search(
            @Query("term") String term,
            @Query("limit") int limit
    );

}
