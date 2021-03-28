package com.abhi41.tvshowappmvvm.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.abhi41.tvshowappmvvm.database.TVShowsDatabase;
import com.abhi41.tvshowappmvvm.model.TvShows;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchListViewModel extends AndroidViewModel {

    private TVShowsDatabase tvShowsDatabase;

    public WatchListViewModel(@NonNull Application application) {
        super(application);
        tvShowsDatabase = TVShowsDatabase.getTvShowsDatabase(application);
    }

    public Flowable<List<TvShows>> loadWatchlist()
    {
        return tvShowsDatabase.tvShowDao().getWatchlist();
    }

    public Completable removeTVShowFromWatchlist(TvShows tvShows)
    {
        return tvShowsDatabase.tvShowDao().removeFromWatchlist(tvShows);
    }

}
