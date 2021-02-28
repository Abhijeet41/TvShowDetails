package com.abhi41.tvshowappmvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi41.tvshowappmvvm.R;
import com.abhi41.tvshowappmvvm.databinding.ItemContainerTvBinding;
import com.abhi41.tvshowappmvvm.listeners.TVShowsListener;
import com.abhi41.tvshowappmvvm.model.TvShows;
import com.abhi41.tvshowappmvvm.viewmodels.MostPopularTVShowsViewModel;

import java.util.List;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TVShowViewHolder>{
    private List<TvShows> tvShows;
    private LayoutInflater inflater;
    private TVShowsListener tvShowsListener;


    public TVShowsAdapter(List<TvShows> tvShows,TVShowsListener tvShowsListener) {
        this.tvShows = tvShows;
        this.tvShowsListener = tvShowsListener;
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null)
        {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvBinding tvBinding = DataBindingUtil.inflate(inflater, R.layout.item_container_tv,parent,false);
        return new TVShowViewHolder(tvBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        holder.bindTVShow(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class TVShowViewHolder extends RecyclerView.ViewHolder
    {
        private ItemContainerTvBinding itemContainerTvBinding;

        public TVShowViewHolder(ItemContainerTvBinding itemContainerTvBinding)
        {
            super(itemContainerTvBinding.getRoot());
            this.itemContainerTvBinding = itemContainerTvBinding;
        }

        public void bindTVShow(TvShows tvShows)
        {
            itemContainerTvBinding.setTvShow(tvShows);
            itemContainerTvBinding.executePendingBindings();
            itemContainerTvBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvShowsListener.onTvShowClicked(tvShows);
                }
            });
        }
    }
}
