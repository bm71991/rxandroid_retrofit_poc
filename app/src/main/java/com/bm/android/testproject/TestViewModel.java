package com.bm.android.testproject;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<QuizQuestion>> mQuestions;
    private String TAG = "TestViewModel";

    public TestViewModel(@NonNull Application application) {
        super(application);
        mQuestions = new MutableLiveData<>();
        loadQuestions();
    }

    public LiveData<ArrayList<QuizQuestion>> getQuestions() {
        return mQuestions;
    }

    private void loadQuestions() {
        String url = "https://opentdb.com/";
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        QuizService service = retrofit.create(QuizService.class);
        Single<QuizResult> testObservable = service.getQuizResults(
                5, 11,"easy", "multiple");

        testObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<QuizResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "in onSubscribe");
                    }

                    @Override
                    public void onSuccess(QuizResult quizResult) {
                        Log.i(TAG, "in onSuccess");
                        Log.i(TAG, quizResult + "");
                        mQuestions.postValue(quizResult.getQuestions());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "in onError: " + e);
                    }
                });
    }
}
