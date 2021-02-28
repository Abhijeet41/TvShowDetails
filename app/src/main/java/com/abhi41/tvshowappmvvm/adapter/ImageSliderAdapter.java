package com.abhi41.tvshowappmvvm.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.abhi41.tvshowappmvvm.R;
import com.abhi41.tvshowappmvvm.databinding.ItemContainerSliderImageBinding;


public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>{
    private String[] sliderImage;
    private LayoutInflater inflater;

    public ImageSliderAdapter(String[] sliderImage) {
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null)
        {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerSliderImageBinding sliderImageBinding = DataBindingUtil.inflate(inflater, R.layout.item_container_slider_image,parent,false);
        return new ImageSliderViewHolder(sliderImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindSliderImage(sliderImage[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }

    class ImageSliderViewHolder extends RecyclerView.ViewHolder
    {
        private ItemContainerSliderImageBinding itemContainerSliderImageBinding;

        public ImageSliderViewHolder(ItemContainerSliderImageBinding itemContainerTvBinding) {
            super(itemContainerTvBinding.getRoot());
            this.itemContainerSliderImageBinding = itemContainerTvBinding;
        }

        public void bindSliderImage(String imageUrl)
        {
            itemContainerSliderImageBinding.setImageURL(imageUrl);

        }

    }
}
