package com.abhi41.tvshowappmvvm.network;

import com.abhi41.tvshowappmvvm.model.TvShows;
import com.abhi41.tvshowappmvvm.response.TVShowDetailsResponse;
import com.abhi41.tvshowappmvvm.response.TvShowsResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("most-popular")
    Observable<TvShowsResponse>getMostPopularTVShows(@Query("page") int page);

    @GET("show-details")
    Observable<TVShowDetailsResponse>getTVShowDetailsResponse(@Query("q") String tvShowId);


}
