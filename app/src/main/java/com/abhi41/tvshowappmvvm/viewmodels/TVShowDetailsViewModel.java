package com.abhi41.tvshowappmvvm.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abhi41.tvshowappmvvm.Repositary.TVShowDetailsRepository;
import com.abhi41.tvshowappmvvm.response.TVShowDetailsResponse;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private TVShowDetailsRepository tvShowDetailsRepository;

    public TVShowDetailsViewModel(@NonNull Application application) {
        super(application);
        tvShowDetailsRepository = new TVShowDetailsRepository();
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId)
    {
        return tvShowDetailsRepository.getTVShowDetails(tvShowId);
    }

}
