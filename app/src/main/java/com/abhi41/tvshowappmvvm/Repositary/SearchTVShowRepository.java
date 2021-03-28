package com.abhi41.tvshowappmvvm.Repositary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abhi41.tvshowappmvvm.network.ApiClient;
import com.abhi41.tvshowappmvvm.network.ApiInterface;
import com.abhi41.tvshowappmvvm.response.TvShowsResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchTVShowRepository {

    private ApiInterface apiInterface;

    public SearchTVShowRepository() {
        apiInterface = ApiClient.getRetrofitCLient();
    }

    public LiveData<TvShowsResponse> searchTVShow(String query,int page)
    {
        MutableLiveData<TvShowsResponse> data = new MutableLiveData<>();

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(apiInterface.searchTVShow(query, page)
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
                                }

                                @Override
                                public void onComplete() {

                                }
                            }));

        return data;
    }
}
