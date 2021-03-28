package com.abhi41.tvshowappmvvm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.abhi41.tvshowappmvvm.R;
import com.abhi41.tvshowappmvvm.adapter.WatchlistAdapter;
import com.abhi41.tvshowappmvvm.dao.TVShowDao;
import com.abhi41.tvshowappmvvm.databinding.ActivityWatchlistactivityBinding;
import com.abhi41.tvshowappmvvm.listeners.WatchlistListener;
import com.abhi41.tvshowappmvvm.model.TvShows;
import com.abhi41.tvshowappmvvm.viewmodels.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class Watchlistactivity extends AppCompatActivity implements WatchlistListener {

    private ActivityWatchlistactivityBinding watchlistactivityBinding;
    private WatchListViewModel viewModel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private WatchlistAdapter watchlistAdapter;
    private List<TvShows> watchlist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        watchlistactivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_watchlistactivity);
        doInitialization();
    }

    private void doInitialization() {
        viewModel = new WatchListViewModel(getApplication());
        watchlistactivityBinding.imgBack.setOnClickListener(v -> onBackPressed());
        watchlist = new ArrayList<>();
    }

    private void loadWatchlist() {
        //watchlistactivityBinding.setIsLoading(true);
        compositeDisposable.add(viewModel.loadWatchlist().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {

                    if (watchlist.size() > 0)
                    {
                        watchlist.clear();
                    }
                    watchlistactivityBinding.setIsLoading(false);
                    watchlist.addAll(tvShows);
                    watchlistAdapter = new WatchlistAdapter(watchlist,this);
                    watchlistactivityBinding.watchListRecylerview.setAdapter(watchlistAdapter);
                    watchlistactivityBinding.watchListRecylerview.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();
                    Toast.makeText(this, "watchlist:" +tvShows.size(), Toast.LENGTH_SHORT).show();
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWatchlist();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public void onTvShowClicked(TvShows tvShows) {
        Intent intent = new Intent(getApplicationContext(),TVShowDetailsActivity.class);
        intent.putExtra("tvShow",tvShows);
        startActivity(intent);
    }

    @Override
    public void removeTvShowFromWatchlist(TvShows tvShows, int position) {

    }
}