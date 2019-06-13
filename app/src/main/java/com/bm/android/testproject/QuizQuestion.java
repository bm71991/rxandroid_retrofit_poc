package com.bm.android.testproject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuizQuestion {
    String difficulty;
    String question;
    @SerializedName("correct_answer")
    String correctAnswer;
    @SerializedName("incorrect_answers")
    ArrayList<String> incorrectAnswers;

    String getDifficulty()  {
        return difficulty;
    }

    ArrayList<String> getIncorrectAnswers()  {
        return incorrectAnswers;
    }

    String getCorrectAnswer()   {
        return correctAnswer;
    }

    String getQuestion()    {
        return question;
    }
}
