package com.bm.android.testproject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuizResult {
    @SerializedName("results")
    ArrayList<QuizQuestion> questions;

    ArrayList<QuizQuestion> getQuestions()   {
        return questions;
    }
}
