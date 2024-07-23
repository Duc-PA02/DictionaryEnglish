package com.example.appdictionaryghtk.service.translate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TranslateServiceApi {
    @GET("translate_a/t")
    Call<String> translateText(
            @Query("client") String client,
            @Query("sl") String sourceLanguage,
            @Query("tl") String targetLanguage,
            @Query("dt") String dt,
            @Query("q") String text
    );
}
