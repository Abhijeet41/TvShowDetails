package com.abhi41.tvshowappmvvm.Repositary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi41.tvshowappmvvm.model.TvShows;
import com.abhi41.tvshowappmvvm.network.ApiClient;
import com.abhi41.tvshowappmvvm.network.ApiInterface;
import com.abhi41.tvshowappmvvm.response.TvShowsResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MostPopularTVShowsRepository {

    ApiInterface apiService;

    public MostPopularTVShowsRepository() {
        this.apiService = ApiClient.getRetrofitCLient();
    }

    public LiveData<TvShowsResponse> getMostPopularTVShows(int page)
    {
        MutableLiveData<TvShowsResponse> data = new MutableLiveData<>();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(apiService.getMostPopularTVShows(page)
                           .subscribeOn(Schedulers.io())
                           .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<TvShowsResponse>() {

                                @Override
                                public void onNext(@NonNull TvShowsResponse tvShowsResponse) {
                                    data.setValue(tvShowsResponse);
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
