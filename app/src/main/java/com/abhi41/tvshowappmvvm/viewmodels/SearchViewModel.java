package com.abhi41.tvshowappmvvm.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abhi41.tvshowappmvvm.Repositary.SearchTVShowRepository;
import com.abhi41.tvshowappmvvm.Repositary.TVShowDetailsRepository;
import com.abhi41.tvshowappmvvm.response.TvShowsResponse;

public class SearchViewModel extends AndroidViewModel {
    private SearchTVShowRepository searchTVShowRepository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        searchTVShowRepository = new SearchTVShowRepository();
    }

    public LiveData<TvShowsResponse> searchTvshow (String query, int page)
    {
        return searchTVShowRepository.searchTVShow(query, page);
    }

}
