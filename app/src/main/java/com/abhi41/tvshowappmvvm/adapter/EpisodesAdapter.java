package com.abhi41.tvshowappmvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi41.tvshowappmvvm.R;
import com.abhi41.tvshowappmvvm.databinding.ItemContainerEpisodesBinding;
import com.abhi41.tvshowappmvvm.databinding.LayoutEpisodesBottomSheetBinding;
import com.abhi41.tvshowappmvvm.model.Episode;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>{

    private List<Episode> episodes;
    private LayoutInflater layoutInflater;


    public EpisodesAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerEpisodesBinding itemContainerEpisodesBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_episodes,parent,false);

        return new EpisodeViewHolder(itemContainerEpisodesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        holder.bindEpisodes(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    static class EpisodeViewHolder extends RecyclerView.ViewHolder
    {
        private ItemContainerEpisodesBinding itemContainerEpisodesBinding;

        public EpisodeViewHolder(ItemContainerEpisodesBinding itemContainerEpisodesBinding) {
            super(itemContainerEpisodesBinding.getRoot());
            this.itemContainerEpisodesBinding = itemContainerEpisodesBinding;
        }

        public void bindEpisodes(Episode episode)
        {
            String title = "s";
            String season = episode.getSeason();
            if (season.length() == 1)
            {
                season = "0".concat(season);
            }

            String episodeNumber = episode.getEpisode();
            if (episodeNumber.length() == 1)
            {
                episodeNumber = "0".concat(episodeNumber);
            }
            episodeNumber = "E".concat(episodeNumber);
            title = title.concat(season).concat(episodeNumber);
            itemContainerEpisodesBinding.setTitle(title);
            itemContainerEpisodesBinding.setName(episode.getName());
            itemContainerEpisodesBinding.setAirDate(episode.getAir_date());
        }
    }
}
