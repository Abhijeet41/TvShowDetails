package com.abhi41.tvshowappmvvm.dao;

import android.database.Observable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abhi41.tvshowappmvvm.model.TvShows;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.internal.operators.flowable.FlowableAll;

@Dao
public interface TVShowDao {

    @Query("SELECT * FROM tvShows")
    Flowable <List<TvShows>> getWatchlist();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //long addToWatchlist(TvShows tvShows);
    Completable addToWatchlist(TvShows tvShows);

    @Delete
    Completable removeFromWatchlist(TvShows tvShows);


}
