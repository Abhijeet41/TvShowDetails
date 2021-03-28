package com.abhi41.tvshowappmvvm.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "tvShows")
public class TvShows implements Serializable {

    @PrimaryKey
    @NotNull
    String id;
    String name;
    String permalink;
    String start_date;
    String country;
    String network;
    String status;
    String image_thumbnail_path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage_thumbnail_path() {
        return image_thumbnail_path;
    }

    public void setImage_thumbnail_path(String image_thumbnail_path) {
        this.image_thumbnail_path = image_thumbnail_path;
    }

}
