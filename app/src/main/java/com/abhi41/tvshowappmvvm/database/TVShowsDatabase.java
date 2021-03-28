package com.abhi41.tvshowappmvvm.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.abhi41.tvshowappmvvm.dao.TVShowDao;
import com.abhi41.tvshowappmvvm.model.TvShows;

@Database(entities = TvShows.class, version = 1, exportSchema = false)
public abstract class TVShowsDatabase extends RoomDatabase {

    private static TVShowsDatabase tvShowsDatabase;
    public static synchronized TVShowsDatabase getTvShowsDatabase(Context context)//"Synchronized" method is a method which can be used by only one thread at a time
    {
        if(tvShowsDatabase == null)
        {
            tvShowsDatabase = Room.databaseBuilder(context,TVShowsDatabase.class,
                    "tv_show_db").build();

        }
        return tvShowsDatabase;
    }

    public abstract TVShowDao tvShowDao();

}
