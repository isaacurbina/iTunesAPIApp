package com.mac.isaac.itunesapiapp.retrofit;

import com.mac.isaac.itunesapiapp.pojos.Result;
import com.mac.isaac.itunesapiapp.pojos.Results;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RFHelper {

    public static void main(String[] args) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RFInterface rfInterface = retrofit.create(RFInterface.class);

        // TEST CASE FOR MEDIA BY MOVIE ID
        Call<Results> request = rfInterface.search(
                "iglesias",
                25
        );

        Results results = null;

        try {
            results = request.execute().body();
            for (Result result : results.getResults()) {
                System.out.println(result.getArtistName() +" / "+result.getTrackName());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}