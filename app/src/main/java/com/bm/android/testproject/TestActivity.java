package com.bm.android.testproject;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {
    private String TAG = "TestActivity";
    private ProgressBar mProgressBar;
    private TextView mQuestionTextView;
    private TestViewModel mTestViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mQuestionTextView = (TextView) findViewById(R.id.questionText);
        mTestViewModel = ViewModelProviders.of(this).get(TestViewModel.class);

        LiveData<ArrayList<QuizQuestion>> questions = mTestViewModel.getQuestions();
        /*If the questions have not been loaded in the ViewModel yet: */
        if (questions.getValue() == null)  {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
            /*Observe when the questions have been loaded by the ViewModel*/
            questions.observe(this, new Observer<ArrayList<QuizQuestion>>() {
                @Override
                public void onChanged(@Nullable ArrayList<QuizQuestion> quizQuestions) {
                    displayQuestion(quizQuestions, 0);
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            });
            /*If the questions have already been loaded in the ViewModel:*/
        } else {
            displayQuestion(questions.getValue(), 0);
        }
    }

    private void displayQuestion(ArrayList<QuizQuestion> quizQuestions, int questionNumber)    {
        QuizQuestion questionToDisplay = quizQuestions.get(questionNumber);
        String questionString = questionToDisplay.getQuestion();
        if (mQuestionTextView.getVisibility() == View.INVISIBLE)    {
            mQuestionTextView.setVisibility(View.VISIBLE);
        }
        mQuestionTextView.setText(Html.fromHtml(questionString));
    }
}
