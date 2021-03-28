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
import com.abhi41.tvshowappmvvm.listeners.WatchlistListener;
import com.abhi41.tvshowappmvvm.model.TvShows;

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.TVShowViewHolder>{
    private List<TvShows> tvShows;
    private LayoutInflater inflater;
    private WatchlistListener watchlistListener;


    public WatchlistAdapter(List<TvShows> tvShows, WatchlistListener watchlistListener) {
        this.tvShows = tvShows;
        this.watchlistListener = watchlistListener;
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
            itemContainerTvBinding.getRoot().setOnClickListener(v -> watchlistListener.onTvShowClicked(tvShows));
            itemContainerTvBinding.imageDelete.setOnClickListener(v -> watchlistListener.removeTvShowFromWatchlist(tvShows,getAdapterPosition()));
            itemContainerTvBinding.imageDelete.setVisibility(View.VISIBLE);

        }
    }
}
