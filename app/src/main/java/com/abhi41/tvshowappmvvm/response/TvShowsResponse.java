package com.abhi41.tvshowappmvvm.response;

import com.abhi41.tvshowappmvvm.model.TvShows;

import java.util.List;

public class TvShowsResponse {
    private String page;
    private int total;
    private List<TvShows> tv_shows;

    public int getTotalpages() {
        return total;
    }

    public List<TvShows> getTv_shows() {
        return tv_shows;
    }



}
