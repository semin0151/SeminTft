package com.example.mytft;

import com.example.mytft.Data.Result;
import com.example.mytft.Data.SummonerID;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("summoner/v1/summoners/by-name/{name}")
    Call<SummonerID>
    getID(@Path("name") String summonerName, @Query("api_key") String api_key);

    @GET("league/v1/entries/by-summoner/{ID}")
    Call<ArrayList<Result>>
    getInf(@Path("ID") String ID, @Query("api_key") String api_key);
}
