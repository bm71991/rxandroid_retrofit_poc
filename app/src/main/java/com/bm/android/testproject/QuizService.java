package com.bm.android.testproject;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuizService {
    @GET("/api.php")
    public Single<QuizResult> getQuizResults(
            @Query("amount") int amount,
            @Query("category") int category,
            @Query("difficulty") String difficulty,
            @Query("type") String type);
}
