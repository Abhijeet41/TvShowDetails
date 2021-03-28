package com.abhi41.tvshowappmvvm.listeners;

import com.abhi41.tvshowappmvvm.model.TvShows;

public interface WatchlistListener {

    void onTvShowClicked(TvShows tvShows);

    void removeTvShowFromWatchlist(TvShows tvShows, int position);



}
