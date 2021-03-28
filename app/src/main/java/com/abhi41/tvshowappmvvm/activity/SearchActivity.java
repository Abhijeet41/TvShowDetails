package com.abhi41.tvshowappmvvm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.abhi41.tvshowappmvvm.R;
import com.abhi41.tvshowappmvvm.adapter.TVShowsAdapter;
import com.abhi41.tvshowappmvvm.databinding.ActivitySearchBinding;
import com.abhi41.tvshowappmvvm.listeners.TVShowsListener;
import com.abhi41.tvshowappmvvm.model.TvShows;
import com.abhi41.tvshowappmvvm.response.TvShowsResponse;
import com.abhi41.tvshowappmvvm.viewmodels.MostPopularTVShowsViewModel;
import com.abhi41.tvshowappmvvm.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TVShowsListener {

    ActivitySearchBinding activitySearchBinding;
    private SearchViewModel searchViewModel;
    private List<TvShows> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        doInitilization();
    }

    private void doInitilization() {
        activitySearchBinding.imageBack.setOnClickListener(v -> onBackPressed());
        activitySearchBinding.tvShowRecyclerView.setHasFixedSize(true);
        //searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel = new SearchViewModel(getApplication());
        tvShowsAdapter = new TVShowsAdapter(tvShows, this);
        activitySearchBinding.tvShowRecyclerView.setAdapter(tvShowsAdapter);

        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    currentPage = 1;
                                    totalAvailablePages = 1;
                                    searchTVshow(editable.toString());
                                }
                            });
                        }
                    }, 800);
                } else {
                    tvShows.clear();
                    tvShowsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void searchTVshow(String query) {
        toggleLoading();
        searchViewModel.searchTvshow(query, currentPage).observe(this, new Observer<TvShowsResponse>() {
            @Override
            public void onChanged(TvShowsResponse tvShowsResponse) {
                toggleLoading();
                if (tvShowsResponse != null) {
                    totalAvailablePages = tvShowsResponse.getTotalpages();

                    if (tvShowsResponse.getTv_shows() != null) {
                        int oldCount = tvShows.size();
                        tvShows.addAll(tvShowsResponse.getTv_shows());
                        tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShows.size());

                    }

                }else {
                    tvShows.clear();
                    tvShowsAdapter.notifyDataSetChanged();
                }
            }
        });
        activitySearchBinding.tvShowRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activitySearchBinding.tvShowRecyclerView.canScrollVertically(1))
                {
                    if (!activitySearchBinding.inputSearch.getText().toString().isEmpty())
                    {
                        if (currentPage < totalAvailablePages)
                        {
                            currentPage += 1;
                            searchTVshow(activitySearchBinding.inputSearch.getText().toString());
                        }
                    }

                }

            }
        });
        activitySearchBinding.inputSearch.requestFocus();
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activitySearchBinding.getIsLoading() != null && activitySearchBinding.getIsLoading()) {
                activitySearchBinding.setIsLoading(false);
            } else {
                activitySearchBinding.setIsLoading(true);
            }
        } else {
            if (activitySearchBinding.getIsLoadingMore() != null && activitySearchBinding.getIsLoadingMore()) {
                activitySearchBinding.setIsLoadingMore(false);
            } else {
                activitySearchBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTvShowClicked(TvShows tvShows) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShows);
        startActivity(intent);
    }
}