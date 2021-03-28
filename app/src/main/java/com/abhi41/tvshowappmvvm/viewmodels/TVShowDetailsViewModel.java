package com.abhi41.tvshowappmvvm.viewmodels;

import android.app.Application;
import android.database.Observable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abhi41.tvshowappmvvm.Repositary.TVShowDetailsRepository;
import com.abhi41.tvshowappmvvm.database.TVShowsDatabase;
import com.abhi41.tvshowappmvvm.model.TvShows;
import com.abhi41.tvshowappmvvm.response.TVShowDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private TVShowDetailsRepository tvShowDetailsRepository;
    private TVShowsDatabase tvShowsDatabase;


    public TVShowDetailsViewModel(@NonNull Application application) {
        super(application);
        tvShowDetailsRepository = new TVShowDetailsRepository();
        tvShowsDatabase = TVShowsDatabase.getTvShowsDatabase(application);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId)
    {
        return tvShowDetailsRepository.getTVShowDetails(tvShowId);
    }

    public Completable addToWatchlist(TvShows tvShows)
    {
        return tvShowsDatabase.tvShowDao().addToWatchlist(tvShows);
    }

}
