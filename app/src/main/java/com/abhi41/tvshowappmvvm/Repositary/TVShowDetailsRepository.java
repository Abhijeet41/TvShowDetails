package com.abhi41.tvshowappmvvm.Repositary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi41.tvshowappmvvm.network.ApiClient;
import com.abhi41.tvshowappmvvm.network.ApiInterface;
import com.abhi41.tvshowappmvvm.response.TVShowDetailsResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TVShowDetailsRepository {
    private ApiInterface apiService;

    public TVShowDetailsRepository() {
        apiService = ApiClient.getRetrofitCLient();
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId)
    {
        MutableLiveData<TVShowDetailsResponse>data = new MutableLiveData<>();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(apiService.getTVShowDetailsResponse(tvShowId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<TVShowDetailsResponse>() {
                                @Override
                                public void onNext(@NonNull TVShowDetailsResponse tvShowDetailsResponse) {
                                    data.setValue(tvShowDetailsResponse);
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    data.setValue(null);
                                    e.printStackTrace();
                                }

                                @Override
                                public void onComplete() {

                                }
                            }));

        return data;
    }
}
