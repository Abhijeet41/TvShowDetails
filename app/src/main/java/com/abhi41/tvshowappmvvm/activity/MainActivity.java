package com.abhi41.tvshowappmvvm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.abhi41.tvshowappmvvm.R;
import com.abhi41.tvshowappmvvm.adapter.TVShowsAdapter;
import com.abhi41.tvshowappmvvm.databinding.ActivityMainBinding;
import com.abhi41.tvshowappmvvm.listeners.TVShowsListener;
import com.abhi41.tvshowappmvvm.model.TvShows;
import com.abhi41.tvshowappmvvm.response.TvShowsResponse;
import com.abhi41.tvshowappmvvm.viewmodels.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowsListener {

    private MostPopularTVShowsViewModel viewModel;
    private ActivityMainBinding activityMainBinding;
    private List<TvShows> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MostPopularTVShowsViewModel(getApplication());
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        doInitialization();
    }

    private void doInitialization() {
        activityMainBinding.rvTvShow.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows,this);
        activityMainBinding.rvTvShow.setAdapter(tvShowsAdapter);
        activityMainBinding.rvTvShow.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.rvTvShow.canScrollVertically(1))
                {
                    if (currentPage <= totalAvailablePages)
                    {
                        currentPage = currentPage + 1;
                        getMostPopularTVShows();
                    }
                }
            }
        });
        activityMainBinding.imgWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Watchlistactivity.class));
            }
        });

        activityMainBinding.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SearchActivity.class));
            }
        });
        

        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        //activityMainBinding.setIsLoading(true);
        toggleLoading();
        viewModel.getMostpopularTVShows(currentPage).observe(this, tvShowsResponses -> {
            //activityMainBinding.setIsLoading(false);
            toggleLoading();
            if (tvShowsResponses != null) {
                totalAvailablePages = tvShowsResponses.getTotalpages();
                if (tvShowsResponses.getTv_shows() != null) {
                    int oldCount = tvShows.size();
                    tvShows.addAll(tvShowsResponses.getTv_shows());
                    //tvShowsAdapter.notifyDataSetChanged();
                    tvShowsAdapter.notifyItemRangeInserted(oldCount,tvShows.size());
                  /* I discover that using notifyItemRangeChange(startPosition, list.size())
                    is the correct way of updating the adapter position without
                    using notifyDataSetChanged().It is useful since it will only update items from the given
                    startPosition up to the last item instead of refreshing the whole list.*/
                }
            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()) {
                activityMainBinding.setIsLoading(false);
            }else {
                activityMainBinding.setIsLoading(true);
            }
        }else {
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore())
            {
                activityMainBinding.setIsLoadingMore(false);
            }else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTvShowClicked(TvShows tvShows) {
            Intent intent = new Intent(getApplicationContext(),TVShowDetailsActivity.class);

            intent.putExtra("tvShow",tvShows);

            /*intent.putExtra("id",tvShows.getId());
            intent.putExtra("name",tvShows.getName());
            intent.putExtra("startDate",tvShows.getStart_date());
            intent.putExtra("country",tvShows.getCountry());
            intent.putExtra("network",tvShows.getNetwork());
            intent.putExtra("status",tvShows.getStatus());*/

            startActivity(intent);
    }
}