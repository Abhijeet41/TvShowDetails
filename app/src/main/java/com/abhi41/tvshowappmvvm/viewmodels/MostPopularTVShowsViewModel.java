package com.abhi41.tvshowappmvvm.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abhi41.tvshowappmvvm.Repositary.MostPopularTVShowsRepository;
import com.abhi41.tvshowappmvvm.response.TvShowsResponse;

import java.util.List;

public class MostPopularTVShowsViewModel extends AndroidViewModel {

    private MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTVShowsViewModel(@NonNull Application application) {
        super(application);
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }

    public LiveData<TvShowsResponse> getMostpopularTVShows(int page)
    {
        return mostPopularTVShowsRepository.getMostPopularTVShows(page);
    }

}
